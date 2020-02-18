package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VacancyCrawler {
	private static final Logger LOG = LoggerFactory.getLogger(VacancyCrawler.class);
	private boolean expiredDateFlag = false;
	private Date expiredDate;
	private final int timeout;


	public VacancyCrawler(Date expiredDate, int timeout) {
		this.expiredDate = expiredDate;
		this.timeout = timeout;
	}

	public List<Vacancy> parseForum() {
		var result = new ArrayList<Vacancy>();
		var pageAmount = this.pageCount();
		var page = 1;
		LOG.info("Start searching vacancies from " + expiredDate);
		while (!this.expiredDateFlag && page <= pageAmount) {
			var address = String.format("https://www.sql.ru/forum/job-offers/%d", page);
			result.addAll(this.parsePage(address));
			page++;
		}
		LOG.info("Searching complete");
		return result;
	}



	private List<Vacancy> parsePage(String address) {
		List<Vacancy> result = new ArrayList<>();
		try {
			Document doc = Jsoup.connect(address).timeout(this.timeout).get();
			Elements posts = doc.select("tr");
			for (Element tab : posts) {
				var post = tab.getElementsByClass("postslisttopic");
				var name = post.text();
				if (this.nameCheck(name)) {
					var href = post.select("a[href]").first();
					var topicRefer = href.getElementsByAttribute("href").first().attr("href");
					doc = Jsoup.connect(topicRefer).timeout(this.timeout).get();
					var msg = doc.getElementsByClass("msgBody").get(1).text();
					var date = tab.getElementsByClass("altCol").last().text();
					var parsedDate = DateConverter.parseDate(date);
					if (this.expiredDate.after(parsedDate)
							|| this.expiredDate.getTime() == parsedDate.getTime()) {
						expiredDateFlag = true;
						break;
					}
					result.add(new Vacancy(name, msg, topicRefer, parsedDate));
				}
			}
		} catch (IOException e) {
			expiredDateFlag = true;
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	private int pageCount() {
		Document doc = null;
		try {
			doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/").timeout(timeout).get();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		//noinspection ConstantConditions
		Elements tableElem = doc.getElementsByClass("sort_options");
		var num = tableElem.last().getElementsByTag("a").last().text();
		return Integer.parseInt(num);
	}

	private boolean nameCheck(String name) {
		name = name.toLowerCase();
		return name.contains("java")
				&& !name.contains("javascript")
				&& !name.contains("java script");
	}
}
