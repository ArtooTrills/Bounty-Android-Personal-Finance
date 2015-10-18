package utills;

import java.text.SimpleDateFormat;
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
}
