package bounty.android.example.com.bounty;

/**
 * Created by RamizMehran on 27/10/2015.
 */
public class Sms {
    private String from;
    private String type;
    private String amount;

    public Sms(){}

    public Sms(String from, String type, String amount) {
        this.from = from;
        this.type = type;
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
