package com.example.earthshaker.moneybox.budget.eventbus;

import com.example.earthshaker.moneybox.budget.BudgetConfig;

/**
 * Created by earthshaker on 14/5/17.
 */

public class BudgteEventBus {

    public static class OpenBudgetDetailEvent {
        BudgetConfig budgetConfig;

        public BudgetConfig getBudgetConfig() {
            return budgetConfig;
        }

        public OpenBudgetDetailEvent(BudgetConfig budgetConfig) {

            this.budgetConfig = budgetConfig;
        }
    }

    public static class OpenBudget{}

    public static class AddCategoryEvent {
    }

    public static class AddExpenseEvent {
    }

    public static class OpenBudgetDialog {
        BudgetConfig budgetConfig;

        public BudgetConfig getBudgetConfig() {
            return budgetConfig;
        }

        public OpenBudgetDialog(BudgetConfig budgetConfig) {
            this.budgetConfig = budgetConfig;
        }
    }
}
