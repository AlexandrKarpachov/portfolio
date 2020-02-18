package parser;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class ScheduleRunner implements Job {

	public static void main(String[] args) {
//		JobDetail job = JobBuilder.newJob(ScheduleRunner.class)
//				.withIdentity("JobName", "group1")
//				.build();
//		Trigger trigger = TriggerBuilder
//				.newTrigger()
//				.withIdentity("TriggerName", "group1")
//				.withSchedule(
//						CronScheduleBuilder.cronSchedule("0 0 12 * * ?"))
//				.build();
//		try {
//			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//			scheduler.start();
//			scheduler.scheduleJob(job, trigger);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
		var config = new Config().init();
		try (var sqlHandler = new PostgresHandler(config).init()) {
			var timeout = ScheduleRunner.getTimeout(config);
			var date = sqlHandler.startDate();
			var crawler = new VacancyCrawler(date, timeout);
			var vacancies = crawler.parseForum();
			sqlHandler.loadToBase(vacancies);
		}

	}

	@Override
	public void execute(JobExecutionContext context) {
		var config = new Config().init();
		try (var sqlHandler = new PostgresHandler(config).init()) {
			var timeout = this.getTimeout(config);
			var date = sqlHandler.startDate();
			var crawler = new VacancyCrawler(date, timeout);
			var vacancies = crawler.parseForum();
			sqlHandler.loadToBase(vacancies);
		}
	}

	/**
	 * returns timeout which used in Jsoup for connecting to site.
	 * @param config initiated instance of {@link Config}
	 * @return parsed value from file "parser.properties" or zero if this value wasn't
	 * written.
	 */
	private static int getTimeout(Config config) {
		var result = 0;
		var timeout = config.get("json.timeout");
		if (timeout != null) {
			result = Integer.parseInt(timeout);
		}
		return result;
	}
}
