package com.chandilsachin.personal_finance.modules.addExpense

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat.getExtras
import android.os.Bundle
import android.telephony.SmsMessage
import android.provider.Telephony
import com.chandilsachin.personal_finance.database.entities.Expense
import com.chandilsachin.personal_finance.modules.readMessage.ReadMessageViewModel
import java.util.*


/**
 * Created by sachin on 1/1/18.
 */
class MessageReadBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent?.getAction()) {
            val expenseList = mutableListOf<Expense>()
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                val messageBody = smsMessage.messageBody
                val (remark, spend, amount) = ReadMessageViewModel.extractSMSInfo(messageBody)
                val expense = Expense(remark, amount, spend, Date(smsMessage.timestampMillis))
                expenseList.add(expense)
            }

            val viewModel = AddExpenseViewModel()
            viewModel.addExpenses(expenseList)
        }
    }
}