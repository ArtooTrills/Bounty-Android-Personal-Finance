package bounty.android.example.com.bounty;

/**
 * Created by RamizMehran on 25/10/2015.
 */
public class Expense {
    private String reason;
    private double amt;
    private boolean isForOnce;

    public Expense(){}

    public Expense(String reason, double amt, boolean isForOnce) {
        this.reason = reason;
        this.amt = amt;
        this.isForOnce = isForOnce;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public boolean isForOnce() {
        return isForOnce;
    }

    public void setIsForOnce(boolean isForOnce) {
        this.isForOnce = isForOnce;
    }
}
