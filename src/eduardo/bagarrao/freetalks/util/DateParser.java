package eduardo.bagarrao.freetalks.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Eduardo
 * 
 * Class that returns parts of a java.util.Date object.
 *
 */
public class DateParser {

	/**
	 * Calendar instance.
	 */
	private static Calendar cal = Calendar.getInstance();
	
	/**
	 * date format that is intended to use.
	 */
	private static final String STRING_DATE_FORMAT = "dd-M-yyyy HH:mm:ss";
	
	/**
	 * Simple date format created from the {@link #STRING_DATE_FORMAT}
	 */
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(STRING_DATE_FORMAT);

	/**
	 * From a date in string format returns a Date object.
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date) throws ParseException {
		return SIMPLE_DATE_FORMAT.parse(date);
	}

	/**
	 * From a Date object converts to a string with the {@link #STRING_DATE_FORMAT} format.
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String parseString(Date date) throws ParseException {
		return SIMPLE_DATE_FORMAT.format(date);
	}

	/**
	 * returns the seconds of a date.
	 * @param date
	 * @return
	 */
	public static int getSeconds(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.SECOND);
	}

	/**
	 * returns the minutes of a date.
	 * @param date
	 * @return
	 */
	public static int getMinutes(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * returns the hours of a date.
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * returns the day of a date.
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * returns the month of a date.
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	/**
	 * returns the year of a date.
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
}
