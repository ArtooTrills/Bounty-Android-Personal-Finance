package com.example.earthshaker.moneybox.budget.dao;

import com.example.earthshaker.moneybox.common.dao.db.Contract;

/**
 * Created by earthshaker on 14/5/17.
 */

public class BugdetInfoQuery {

    public static String getAllBudgets() {
        return "select * from " +
                Contract.Budget.TABLE_NAME ;
    }


}
