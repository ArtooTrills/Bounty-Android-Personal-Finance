package bounty.android.example.com.bounty;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by RamizMehran on 30/10/2015.
 */
public class GenerateReportActivity extends Activity {

    private TextView mInputIncome;
    private TextView mInputExpense;
    private TextView mInputSaving;

    private TextView mSmsIncome;
    private TextView mSmsExpense;
    private TextView mSmsSaving;

    private TextView mNetIncome;
    private TextView mNetExpense;
    private TextView mNetSaving;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_report);

        mInputIncome = (TextView) findViewById(R.id.input_income_out);
        mInputExpense = (TextView) findViewById(R.id.input_expense_out);
        mInputSaving = (TextView) findViewById(R.id.input_saving_out);

        mSmsExpense = (TextView) findViewById(R.id.sms_expense_out);
        mSmsIncome = (TextView) findViewById(R.id.sms_income_out);
        mSmsSaving = (TextView) findViewById(R.id.sms_saving_out);

        mNetExpense = (TextView) findViewById(R.id.net_expense_out);
        mNetIncome = (TextView) findViewById(R.id.net_income_out);
        mNetSaving = (TextView) findViewById(R.id.net_saving_out);

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.getAllTotal();

        double input_income = dbHandler.getTotal("input income");
        double input_expense = dbHandler.getTotal("input expense");
        double input_saving = input_income - input_expense;

        mInputIncome.setText(input_income+"");
        mInputExpense.setText(input_expense+"");
        mInputSaving.setText(input_saving+"");

        double sms_income = dbHandler.getTotal("sms income");
        double sms_expense = dbHandler.getTotal("sms expense");
        double sms_saving = sms_income - sms_expense;

        mSmsIncome.setText(sms_income+"");
        mSmsExpense.setText(sms_expense+"");
        mSmsSaving.setText(sms_saving+"");

        mNetIncome.setText((input_income+sms_income)+"");
        mNetExpense.setText((input_expense+sms_expense)+"");
        mNetSaving.setText((input_saving+sms_saving)+"");

        dbHandler.addTotal(new Total_("net income", (input_income+sms_income)));
        dbHandler.addTotal(new Total_("net expense",(input_expense+sms_expense)));
        dbHandler.addTotal(new Total_("net saving",(input_saving+sms_saving)));

    }


}
