package bounty.android.example.com.bounty;

/**
 * Created by RamizMehran on 26/10/2015.
 */
public class Total_ {
    private String source;
    private Double amount;

    public Total_(){
    }

    public Total_(String source, Double amount) {
        this.source = source;
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
