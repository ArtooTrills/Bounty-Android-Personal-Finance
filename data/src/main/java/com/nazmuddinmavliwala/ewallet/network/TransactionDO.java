package com.nazmuddinmavliwala.ewallet.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionDO {

    @SerializedName("id")
    private Long id;

    @SerializedName("transaction_type")
    private int trasanctionType;

    @SerializedName("transaction_amount")
    private Long amount;

    @SerializedName("expense_type")
    private String expenseType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTrasanctionType() {
        return trasanctionType;
    }

    public void setTrasanctionType(int trasanctionType) {
        this.trasanctionType = trasanctionType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }
}
