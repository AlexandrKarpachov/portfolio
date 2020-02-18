package parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aleksandr Karpachov
 * @version $Id$
 * @since 03.08.2019
 */
public class DateConverter {
	private static final Map<String, Integer> MONTHS = new HashMap<>() {
		{
			put("янв", Calendar.JANUARY);
			put("фев", Calendar.FEBRUARY);
			put("мар", Calendar.MARCH);
			put("апр", Calendar.APRIL);
			put("май", Calendar.MAY);
			put("июн", Calendar.JUNE);
			put("июл", Calendar.JULY);
			put("авг", Calendar.AUGUST);
			put("сен", Calendar.SEPTEMBER);
			put("окт", Calendar.OCTOBER);
			put("ноя", Calendar.NOVEMBER);
			put("дек", Calendar.DECEMBER);
		}
	};

	private static class Patterns {
		static final String HOURS = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
		static final String DAYS = "([0-2]?[0-9]|3[01])";
		static final String MONTHS = "(янв|фев|мар|апр|май|июн|июл|авг|сен|окт|ноя|дек)";
		static final String YEARS =  "[0-9][0-9]";
	}

	/**
	 *  Converts input date into {@link java.util.Date}
	 * @param date in format like "11 сен 2018, 11:08" or "Сегодня, 21:07"
	 */
	public static Date parseDate(String date) {
		if (!DateConverter.validate(date)) {
			var msg = String.format("Expected date like \"11 сен 2018, 11:08\", or \"вчера, 23:00\", but was %s", date);
			throw new DateConverter().new InvalidTimeFormatException(msg);
		}
		Calendar cal = new GregorianCalendar();
		var values = date.split("(\\s+|,\\s*|:)");
		var hour = Integer.parseInt(values[values.length - 2]);
		var minutes = Integer.parseInt(values[values.length - 1]);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if (values[0].equalsIgnoreCase("вчера")) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		} else if (!values[0].equalsIgnoreCase("сегодня")) {
			var day = Integer.parseInt(values[0]);
			var month = MONTHS.get(values[1]);
			var year = Integer.parseInt(values[2]);
			cal.set(Calendar.DAY_OF_MONTH, day);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.YEAR, year + 2000);
		}
		return cal.getTime();
	}

	/**
	 * Checks input date format if it was incorrect trows {@link InvalidTimeFormatException}
	 */
	private static boolean validate(String date) {
		var datePattern = String.format("((%s\\s%s\\s%s)|сегодня|вчера),\\s%s",
				Patterns.DAYS, Patterns.MONTHS, Patterns.YEARS, Patterns.HOURS);
		Pattern pattern = Pattern.compile(datePattern);
		Matcher matcher = pattern.matcher(date);
		return matcher.matches();
	}

	public class InvalidTimeFormatException extends RuntimeException {
		public InvalidTimeFormatException(String errorMessage) {
			super(errorMessage);
		}
	}
}
