package com.example.earthshaker.moneybox.budget.dao;

import com.example.earthshaker.moneybox.budget.BudgetConfig;
import com.example.earthshaker.moneybox.common.dao.BaseDao;
import com.example.earthshaker.moneybox.common.dao.db.Contract;
import com.example.earthshaker.moneybox.transaction.dao.TransactionInfoDAo;
import com.example.earthshaker.moneybox.transaction.dao.TransactionOrm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthshaker on 14/5/17.
 */

public class BudgetInfoDao extends BaseDao {

    public static List<BudgetConfig> getAllBudgets() {
        List<BudgetConfig> budgetConfigList = new ArrayList<>();
        runRawQuery(BugdetInfoQuery.getAllBudgets(), cursor -> budgetConfigList.add(BudgetOrm.getBudgetConfig(cursor)));
        for (BudgetConfig budgetConfig : budgetConfigList) {
            budgetConfig.setSpent(TransactionInfoDAo.getAmountFromCategory(budgetConfig.getCategory()));
        }
        return budgetConfigList;
    }

    public static List<BudgetConfig> fetchTopTwoBudgets() {
        List<BudgetConfig> budgetConfigList = new ArrayList<>();
        String query = "select * from " + Contract.Budget.TABLE_NAME + " ORDER BY " + Contract.Budget.COLUMN_NAME_AMOUNT + " DESC LIMIT 2";
        runRawQuery(query, cursor -> budgetConfigList.add(BudgetOrm.getBudgetConfig(cursor)));
        return budgetConfigList;
    }
}
