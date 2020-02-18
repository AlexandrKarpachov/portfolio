package parser;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TimeConverterTest {


	@Test
	public void whenPutFullDateAugFormatThenOk() {
		var input = "3 авг 19, 09:20";
		Calendar calendar = new GregorianCalendar();
		calendar.set(2019, Calendar.AUGUST, 3, 9, 20, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		var expected = calendar.getTime();

		var actual = DateConverter.parseDate(input);

		assertThat(expected, is(actual));
	}

	@Test
	public void whenPutFullDateFormatThenOk() {
		var input = "10 июн 19, 12:30";
		Calendar calendar = new GregorianCalendar();
		calendar.set(2019, Calendar.JUNE, 10, 12, 30, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		var expected = calendar.getTime();

		var actual = DateConverter.parseDate(input);

		assertThat(expected, is(actual));
	}

	@Test
	public void whenPutTodayDateFormatThenOk() {
		var input = "сегодня, 12:30";
		Calendar calendar = new GregorianCalendar();

		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		var expected = calendar.getTime();
		var actual = DateConverter.parseDate(input);

		assertThat(expected, is(actual));
	}

	@Test
	public void whenPutYesterdayDateFormatThenOk() {
		var input = "вчера, 12:30";
		Calendar calendar = new GregorianCalendar();

		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		var expected = calendar.getTime();
		var actual = DateConverter.parseDate(input);

		assertThat(expected, is(actual));
	}
}