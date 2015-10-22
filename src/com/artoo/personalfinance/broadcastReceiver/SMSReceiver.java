package com.artoo.personalfinance.broadcastReceiver;

import java.util.List;
import java.util.Locale;

import model.IgnoreItem;
import persistantData.DatabaseHelper;

import com.artoo.personalfinance.services.SMSFilteringService;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

	/**
	 * this is the default status, if this is set user will get a prompt on home
	 * screen seeking consent and user messages will not be intercepted
	 */
	public static final int SMS_DEFAULT_PERMISSION = -1;

	/**
	 * If this permission is set, user will not get consent seeking prompts any
	 * more and broadcast receiver will be disabled, i.e. user messages will not
	 * be intercepted
	 */
	public static final int USER_DENIED_SMS_INTERCEPTION = 0;

	/**
	 * If this permission is set, user will not get consent seeking prompts any
	 * more and broadcast receiver will be enabled, i.e. user messages will be
	 * intercepted
	 */
	public static final int USER_ALLOWED_SMS_INTERCEPTION = 1;

	public static final String SENDER_NAME_KEY = "sender_name";
	public static final String MESSAGE_BODY_KEY = "msg_body";
	char indianRupee = '\u20A8';
	private DatabaseHelper dbHelper;

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {

		final Bundle bundle = intent.getExtras();
		try {
			if (bundle != null) {

				Object[] obj = (Object[]) bundle.get("pdus");

				for (Object o : obj) {

					SmsMessage currentMessage = SmsMessage
							.createFromPdu((byte[]) o);
					String senderAddress = currentMessage
							.getDisplayOriginatingAddress();
					dbHelper = new DatabaseHelper(context);
					boolean isIngored = false;
					List<IgnoreItem> list = dbHelper.getIgnoreItemList();
					for (IgnoreItem i : list) {
						if (i.getSource().equals(
								senderAddress.toUpperCase(Locale.ENGLISH))) {
							isIngored = true;
							break;
						}
					}
					if (!isIngored) {
						Intent smsIntent = new Intent(context,
								SMSFilteringService.class);
						smsIntent.putExtra(SENDER_NAME_KEY, senderAddress);
						smsIntent.putExtra(MESSAGE_BODY_KEY,
								currentMessage.getMessageBody());
						context.startService(smsIntent);
					}

				}
			}
		} catch (Exception e) {

			System.out.println("in rec-- " + e.toString());
		}
	}

	/**
	 * disables broadcast receiver and stop receiving messages
	 * 
	 * @param context
	 */
	public static void disableBroadcastReceiver(Context context) {

		ComponentName receiver = new ComponentName(
				context.getApplicationContext(), SMSReceiver.class);
		PackageManager pm = context.getPackageManager();
		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);

	}

	/**
	 * enables broadcast receiver and start receiving messages
	 * 
	 * @param context
	 */
	public static void enableBroadcastReceiver(Context context) {
		ComponentName receiver = new ComponentName(
				context.getApplicationContext(), SMSReceiver.class);
		PackageManager pm = context.getPackageManager();
		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);
	}
}
