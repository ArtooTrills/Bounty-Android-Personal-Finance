package bounty.android.example.com.bounty;

/**
 * Created by RamizMehran on 25/10/2015.
 */
public class Expense {
    private String src;
    private double amt;
    private boolean isForOnce;

    public Expense(){}

    public Expense(String reason, double amt, boolean isForOnce) {
        this.src = reason;
        this.amt = amt;
        this.isForOnce = isForOnce;
    }

    public String getReason() {
        return src;
    }

    public void setReason(String reason) {
        this.src = reason;
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
