package bounty.android.example.com.bounty;

/**
 * Created by RamizMehran on 25/10/2015.
 */
public class Income {
    private String src;
    private double amt;

    public Income(){}

    public Income(String src, double amt) {
        this.src = src;
        this.amt = amt;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }
}
