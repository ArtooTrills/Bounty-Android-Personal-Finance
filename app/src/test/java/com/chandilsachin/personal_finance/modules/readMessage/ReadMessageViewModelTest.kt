package com.chandilsachin.personal_finance.modules.readMessage

import android.content.Context
import com.chandilsachin.personal_finance.dagger.AppContext
import com.chandilsachin.personal_finance.dagger.DaggerDatabaseComponent
import com.chandilsachin.personal_finance.dagger.MyApplication
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito

class ReadMessageViewModelTest {

    val listSms = mutableListOf<String>()
    val listResult = mutableListOf<ReadMessageViewModel.SMSInfo>()
    @Before
    fun setUp() {
        listSms.add("Your a/c 000000 is debited Rs 155 on 2014-08-29 A/c balance is Rs 2101.32 Info: INB/116574287/PAYU.IN/")
        listResult.add(ReadMessageViewModel.SMSInfo("PAYU.IN",true,155f))

        listSms.add("Your a/c 000000 is debited Rs 39 on 2014-08-28 A/c balance is Rs 2256.32 Info: INB/116498073/TATA DOCOMO (BILL DESK)/")
        listResult.add(ReadMessageViewModel.SMSInfo("TATA DOCOMO",true,39f))

        listSms.add("Your a/c 000000 is debited INR 2000.00 on 06-09-2014 18:12:01 A/c Balance is INR 17946.32 Info: CASH-ATM/SBI  PUN CAC NIGDI PR/PUNE/")
        listResult.add(ReadMessageViewModel.SMSInfo("ATM Debit",false,2000f))

        listSms.add("Your a/c 000000 is debited Rs 2000 on 2016-03-02 A/c balance is Rs XXXXX.XX Info: NEFT/MB/AXMB160628273068/Sachin Chandil")
        listResult.add(ReadMessageViewModel.SMSInfo("Sachin Chandil",true,2000f))

        listSms.add("Your a/c 000000 is debited INR 2157.67 on 13-09-2015 08:57:00 Info: PUR/D MART AVENUE/PUNE/D MART AVENUE")
        listResult.add(ReadMessageViewModel.SMSInfo("D MART AVENUE",true,2157.67f))
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testExtractSmsInfo(){
        for ((i) in listSms.withIndex()) {
            assertEquals(listResult[i],
                    ReadMessageViewModel.extractSMSInfo(listSms[i]))
        }
    }
}