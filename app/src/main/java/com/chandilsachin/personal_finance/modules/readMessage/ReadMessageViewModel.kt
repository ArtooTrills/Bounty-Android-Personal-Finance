package com.chandilsachin.personal_finance.modules.readMessage

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.net.Uri
import android.util.Log
import com.chandilsachin.personal_finance.dagger.MyApplication
import com.chandilsachin.personal_finance.database.LocalRepo
import com.chandilsachin.personal_finance.database.Preferences
import com.chandilsachin.personal_finance.database.entities.Expense
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created by sachin on 31/12/17.
 */
class ReadMessageViewModel : ViewModel() {

    @Inject
    lateinit var localRepo: LocalRepo
    var totalSmsCount = -1
    var smsReadCount = -1
    val smsFinishedReadingLiveData = MutableLiveData<Int>()

    init {
        MyApplication.component.inject(this)
    }

    fun loadSmsIntoDatabase(context: Context) {
        smsReadCount = 0
        getSmsDetails(context)
                .compose(saveExpense())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(smsReadCount % 20 == 0)
                        smsFinishedReadingLiveData.value = smsReadCount
                },{it.printStackTrace()}, {
                    smsFinishedReadingLiveData.value = totalSmsCount
                })
    }

    private fun saveExpense(): ObservableTransformer<Expense, Boolean> {
        return ObservableTransformer {
            it.flatMap { expense ->
                localRepo.addExpense(expense).toObservable()
            }
        }
    }


    @SuppressLint("Recycle")
    private fun getSmsDetails(context: Context): Observable<Expense> {

        return Observable.create<Expense>({ subscriber ->
            val cursor = context.contentResolver.query(Uri.parse("content://sms/inbox"),
                    arrayOf("_id", "thread_id", "address", "person", "date", "body"), null, null, null)
            if (cursor != null) {
                totalSmsCount = cursor.count

                while (cursor.moveToNext()) {
                    smsReadCount++
                    val body = cursor.getString(5)
                    if(body.toLowerCase().contains("debited") || body.toLowerCase().contains("purchased")) {

                        var (remark, spend, amount) = extractSMSInfo(body)
                        if(remark.isNotEmpty() && amount > 0) {
                            val expense = Expense(remark, amount, spend, java.util.Date(cursor.getLong(4)))
                            subscriber.onNext(expense)
                            Log.w("READ", "${amount}- (${remark}) - ${body}")
                        }
                    }else
                        Log.w("READ", "${body}")
                }
            }
            subscriber.onComplete()
        }).subscribeOn(Schedulers.io())
    }

    companion object {

        fun extractSMSInfo(body: String): SMSInfo{

            val info = SMSInfo("",true, 0f)
            if(body.contains("INB")) {
                val group = Regex("(?i)(?:(?:Info:)\\.?\\s?)(?:INB/\\w+/([\\w\\s\\._]+))").find(body)?.groupValues
                group?.let { info.remark = group[group.size - 1] }
                info.spend = true
            }
            else if(body.contains("MOB")){
                val group = Regex("(?i)(?:(?:Info:)\\.?\\s?)(?:MOB/\\w+/([\\w\\s\\._]+))").find(body)?.groupValues
                group?.let { info.remark = group[group.size - 1] }
                info.spend = true
            }
            else if(body.contains("PUR")){
                val group = Regex("(?i)(?:(?:Info:)\\.?\\s?)(?:PUR/([\\w\\s\\._]+))").find(body)?.groupValues
                group?.let { info.remark = group[group.size - 1] }
                info.spend = true
            }
            else if(body.contains("NEFT")){
                val group = Regex("(?i)(?:(?:Info:)\\.?\\s?)(?:NEFT/\\w+/\\w+/([\\w\\s\\._]+))").find(body)?.groupValues
                group?.let { info.remark = group[group.size - 1] }
                info.spend = true
            }
            else if(body.contains("CASH-ATM")){
                info.remark = "ATM Debit"
                info.spend = false
            }
            info.remark = info.remark.trim()
            var amount = Regex("(?i)(?:(?:RS|INR|MRP)(\\.?)\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)").find(body)?.value
            info.amount = amount?.let { Regex("\\d+").find(amount)?.value?.toFloat() }!!
            return info
        }
    }



    data class SMSInfo(var remark: String, var spend: Boolean, var amount: Float)

//Info: CASH-ATM/AXIS BANK LIMITED/PUNE/SAMBHAJI CHOWK
    // Info: INB/117184729/TATA DOCOMO CDMA(BILLDE/
    //Info: INB/116498073/TATA DOCOMO (BILL DESK)
    // Info: INB/116574287/PAYU.IN/
    // Info: INB/119668474/INDIAN RAILWAY CORPORAT/
    // Info: INB/119744697/PAYTM MOBILE SOLUTIONS/
    // Info: MOB/TPFT/JAY KISHOR DWIV/914010007272344
    //  Info: INB/IFT/JAY KISHOR DWIVEDI/

}