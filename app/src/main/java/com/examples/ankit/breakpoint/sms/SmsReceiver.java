package com.examples.ankit.breakpoint.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.examples.ankit.breakpoint.R;
import com.examples.ankit.breakpoint.models.SmsReceivedEvent;
import com.examples.ankit.breakpoint.models.Transaction;
import com.examples.ankit.breakpoint.utils.NotificationsManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ankit on 12/06/17.
 */

public class SmsReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody();
                String address = smsMessage.getOriginatingAddress();

                Transaction transaction = SmsUtil.addSingleSmsToTransaction(address, smsBody, System.currentTimeMillis());
                if (transaction != null) {
                    NotificationsManager manager = NotificationsManager.getInstance();
                    if (SmsUtil.EXPENSE == transaction.getType()) {
                        manager.generateNotification(context.getString(R.string.expense_notification_title),
                                context.getString(R.string.expense_notification_body) + transaction.getAmount());
                    } else if (SmsUtil.INCOME == transaction.getType()) {
                        manager.generateNotification(context.getString(R.string.income_notification_title),
                                context.getString(R.string.income_notification_body) + transaction.getAmount());
                    }
                    EventBus.getDefault().post(new SmsReceivedEvent());
                }
            }
        }
    }
}
