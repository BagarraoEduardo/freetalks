package eduardo.bagarrao.freetalks.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Eduardo
 *
 */
public class DateParser {

	private static Calendar cal = Calendar.getInstance();
	
	/**
	 * 
	 */
	private static final String STRING_DATE_FORMAT = "dd-M-yyyy HH:mm:ss";
	
	/**
	 * 
	 */
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(STRING_DATE_FORMAT);

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date) throws ParseException {
		return SIMPLE_DATE_FORMAT.parse(date);
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String parseString(Date date) throws ParseException {
		return SIMPLE_DATE_FORMAT.format(date);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getSeconds(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.SECOND);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinutes(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
}
