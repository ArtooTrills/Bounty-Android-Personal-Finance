package utills;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * this class is utility class, containing general utility methods
 * 
 * @author nayan
 *
 */
public final class CommonUtility {
	/**
	 * formats a date in dd-mm-yyyy pattern
	 */
	public static final SimpleDateFormat DATE_FORMATTER_WITHOUT_TIME = new SimpleDateFormat(
			"dd-MM-yyyy", Locale.ENGLISH);
	
	/**
	 * utility method to convert long to date
	 * @param currentTime
	 * @return
	 */
	public static Date millisToDate(long currentTime) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(currentTime);
	    Date date = calendar.getTime();
	    return date;
	}
}
