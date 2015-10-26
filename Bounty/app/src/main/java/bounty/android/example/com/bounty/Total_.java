package bounty.android.example.com.bounty;

/**
 * Created by RamizMehran on 26/10/2015.
 */
public class Total_ {
    private double income;
    private double expense;

    public Total_(){
        income = 0;
        expense = 0;
    }

    public Total_(double income, double expense) {
        this.income = income;
        this.expense = expense;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }
}
