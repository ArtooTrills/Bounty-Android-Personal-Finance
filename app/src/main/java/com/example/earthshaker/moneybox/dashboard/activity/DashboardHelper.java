package com.example.earthshaker.moneybox.dashboard.activity;

import com.example.earthshaker.moneybox.common.StringUtils;
import com.example.earthshaker.moneybox.transaction.TransactionConfig;
import com.example.earthshaker.moneybox.transaction.dao.TransactionModificationDao;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by earthshaker on 15/5/17.
 */

public class DashboardHelper {

    public static void createTransaction(Boolean isExpense, String body) {
        TransactionConfig transactionConfig = new TransactionConfig();
        transactionConfig.setAmount(regexGenerator(body));
        transactionConfig.setCategory("Uncategorised");
        transactionConfig.setDate(String.valueOf(new Date()));
        transactionConfig.setExpense(isExpense);
        TransactionModificationDao.saveTransaction(transactionConfig);
    }

    public static Double regexGenerator(String body) {

        Pattern pattern = Pattern.compile("(?i)(?:(?:RS|INR|MRP)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)");
        Matcher matcher = pattern.matcher(body);
        if (matcher.matches()) {
            return StringUtils.getAmount(matcher.group(2));
        }
        return 0d;
    }
}
