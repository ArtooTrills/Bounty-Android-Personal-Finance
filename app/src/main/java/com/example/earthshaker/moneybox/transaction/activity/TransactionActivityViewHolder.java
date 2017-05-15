package com.example.earthshaker.moneybox.transaction.activity;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.common.BaseHolderEventBus;
import com.example.earthshaker.moneybox.transaction.TransactionConfig;
import com.example.earthshaker.moneybox.transaction.dao.TransactionModificationDao;

/**
 * Created by earthshaker on 15/5/17.
 */

public class TransactionActivityViewHolder extends BaseHolderEventBus {

    private RadioGroup txnTypeRg;
    private EditText amount, date, category;
    private LinearLayout save, delete;

    private Context context;
    private TransactionConfig mTransactionConfig;

    public TransactionActivityViewHolder(Context context, View view, TransactionConfig transactionConfig) {
        this.context = context;
        this.mTransactionConfig = transactionConfig;
        txnTypeRg = (RadioGroup) view.findViewById(R.id.txn_type_rg);
        amount = (EditText) view.findViewById(R.id.amount_et);
        date = (EditText) view.findViewById(R.id.date_et);
        category = (EditText) view.findViewById(R.id.category_text);
        save = (LinearLayout) view.findViewById(R.id.save);
        delete = (LinearLayout) view.findViewById(R.id.delete);
        setDataToView();
        setListeners();

    }

    private void setListeners() {
        save.setOnClickListener(l -> {
            if (validateData()) saveData();
        });

        delete.setOnClickListener(l -> deleteData());
    }

    private void deleteData() {
        TransactionModificationDao.deleteTransaction(mTransactionConfig.getId());
    }

    private void saveData() {
        TransactionConfig transactionConfig = new TransactionConfig();
        transactionConfig.setCategory(category.getText().toString().trim());
        transactionConfig.setAmount(Double.parseDouble(amount.getText().toString().trim()));
        transactionConfig.setDate(date.getText().toString().trim());
        transactionConfig.setExpense(txnTypeRg.getId() == R.id.type_debit);
        if (mTransactionConfig != null && mTransactionConfig.getId() != null) {
            TransactionModificationDao.editTransaction(transactionConfig);
        } else {
            TransactionModificationDao.saveTransaction(transactionConfig);
        }
    }

    private boolean validateData() {
        if (amount.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please enter amount", Toast.LENGTH_SHORT).show();
            return false;
        } else if (category.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please enter category", Toast.LENGTH_SHORT).show();
            return false;
        } else if (date.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "Please enter date", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private void setDataToView() {
        if (mTransactionConfig != null) {
            int typeId = mTransactionConfig.getExpense() ? R.id.type_debit : R.id.type_credit;
            txnTypeRg.check(typeId);
            amount.setText(String.valueOf(mTransactionConfig.getAmount()));
            date.setText(mTransactionConfig.getDate());
            category.setText(mTransactionConfig.getCategory());
            delete.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.GONE);
        }
    }


    @Override
    protected void refreshData() {

    }

    @Override
    protected void recreateLayout() {

    }
}
