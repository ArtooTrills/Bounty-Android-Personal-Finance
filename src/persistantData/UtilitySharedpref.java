package persistantData;

import com.artoo.personalfinance.broadcastReceiver.SMSReceiver;

import android.content.Context;
import android.content.SharedPreferences;

public final class UtilitySharedpref {

	private static final String FILE_NAME = "user_data";
	private static final String USER_NAME_KEY = "uname";
	private static final String SMS_PERMISSION_KEY = "sms_permission";
	private static final String ARCHIVE_MESSAGE_READ_KEY = "isOlderMessageRead";

	/**
	 * returns the permission set by user to analyze message, default value is
	 * "SMS_DEFAULT_PERMISSION"
	 * 
	 * @param context
	 * @return
	 */
	public static int getSMSPermission(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILE_NAME, Context.MODE_PRIVATE);
		int permission = Integer.parseInt(sharedPreferences.getString(
				SMS_PERMISSION_KEY, SMSReceiver.SMS_DEFAULT_PERMISSION + ""));
		return permission;
	}

	/**
	 * sets permission as given by user to analyze sms for transactions
	 * 
	 * @param context
	 * @param value
	 */
	public static void setSMSPermission(Context context, int value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(SMS_PERMISSION_KEY, value + "");
		editor.commit();
	}

	/**
	 * returns user name if user has set a name, empty string otherwise
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserName(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILE_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(USER_NAME_KEY, "");
	}

	/**
	 * sets user name, will override if name is already exisiting
	 * 
	 * @param context
	 * @param userName
	 */
	public static void setUserName(Context context, String userName) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(USER_NAME_KEY, userName);
		editor.commit();
	}

	/**
	 * returns if app has analyzed users archive messages or not
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getIsOlderMessageRead(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILE_NAME, Context.MODE_PRIVATE);
		String temp = sharedPreferences.getString(ARCHIVE_MESSAGE_READ_KEY,
				null);
		if (temp == null) {
			return false;
		} else {
			return Boolean.parseBoolean(temp);
		}
	}

	/**
	 * once app has analyzed users all archive messages, this method should be
	 * called to turn off the notifier as true
	 * 
	 * @param context
	 */
	public static void setArchiveMessagesAsRead(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(ARCHIVE_MESSAGE_READ_KEY, String.valueOf(true));
		editor.commit();
	}
}
