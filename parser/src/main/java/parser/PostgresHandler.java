package parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PostgresHandler implements AutoCloseable {
	private final Config config;
	private static final Logger LOG = LoggerFactory.getLogger(PostgresHandler.class);
	private Connection connect;
	private static final String INSERT
			= "INSERT INTO vacancies (\"name\", \"reference\", description, cr_date) VALUES (?, ?, ?, ?)";
	private static final String LAST_VACANCY = "SELECT * FROM vacancies WHERE cr_date = (SELECT MAX(cr_date) FROM vacancies)";

	public PostgresHandler(Config config) {
		this.config = config;
	}

	private void createTable()  {
		//noinspection ConstantConditions
		var file = PostgresHandler.class.getClassLoader().getResource("CreateVacancyTable.sql").getFile();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			Statement stmt = this.connect.createStatement();
			stmt.execute(br.lines().collect(Collectors.joining(" \n")));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public PostgresHandler init() {
		try {
			this.connect = DriverManager.getConnection(
					config.get("url"),
					config.get("username"),
					config.get("password")
			);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		this.createTable();
		return this;
	}

	public Date startDate() {
		Date result = this.lastVacancyDate();
		var date = this.config.get("startTime");
		if (result == null && date == null) {
			result = this.getFirstDayOfTheYear();
		} else if (result == null && this.checkDateFormat(date)) {
			DateFormat parser = new SimpleDateFormat("dd.MM.yyyy");
			try {
				result = parser.parse(date);
			} catch (ParseException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return result;
	}

	private boolean checkDateFormat(String date) {
		var patternRegex = "([0-2]?[0-9]|3[01])\\.([0]?[0-9]|1[0-2])\\.[2][0][0-9][0-9]";
		Pattern pattern = Pattern.compile(patternRegex);
		Matcher matcher = pattern.matcher(date);
		var result = matcher.matches();
		if (!result) {
			LOG.error("Incorrect date format necessary something like this 1.07.2019 but was " + date);
		}
		return  result;
	}

	private Date getFirstDayOfTheYear() {
		var calendar = new GregorianCalendar();
		var year = calendar.get(Calendar.YEAR);
		calendar.set(year, Calendar.JANUARY, 1, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public Date lastVacancyDate() {
		Date result = null;
		try (var statement = this.connect.createStatement()) {
			var vacancy = statement.executeQuery(LAST_VACANCY);
			if (vacancy.next()) {
				result = vacancy.getTimestamp("cr_date");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return result;
	}

	public void loadToBase(List<Vacancy> vacancies) {
		try (var statement = this.connect.prepareStatement(INSERT)) {
			for (Vacancy vac : vacancies) {
				statement.setString(1, vac.getName());
				statement.setString(2, vac.getReference());
				statement.setString(3, vac.getDescription());
				statement.setTimestamp(4, new Timestamp(vac.getDate().getTime()));
				statement.addBatch();
			}
			statement.executeBatch();
			LOG.info(String.format("Were loaded %d vacancies", vacancies.size()));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public void close() {
		try {
			this.connect.close();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}

}
