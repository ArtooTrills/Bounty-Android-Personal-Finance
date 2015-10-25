package bounty.android.example.com.bounty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by RamizMehran on 25/10/2015.
 */
public class IncomeActivity extends Activity{

    private EditText mIncomeSrcAdd;
    private EditText mIncomeAmt;
    private EditText mIncomeSrcDel;
    private Button mAddIncomeSource;
    private Button mDelIncomeSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_page);

        mIncomeSrcAdd = (EditText) findViewById(R.id.new_income_source);
        mIncomeAmt = (EditText) findViewById(R.id.new_income_amount);

        mIncomeSrcDel = (EditText) findViewById(R.id.old_income_source);

        mAddIncomeSource = (Button) findViewById(R.id.add_income);
        mAddIncomeSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newIncome();
                updateList();
            }
        });

        mDelIncomeSource = (Button) findViewById(R.id.delete_income);
        mDelIncomeSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIncome();
                updateList();
            }
        });
    }

    public void newIncome() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String src =
                mIncomeSrcAdd.getText().toString();

        Income in =
                new Income(src,Double.parseDouble(mIncomeAmt.getText().toString()));

        dbHandler.addIncome(in);
        mIncomeSrcAdd.setText("");
        mIncomeAmt.setText("");
    }

    public void updateList () {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        HashMap<String, Double> incomeList = dbHandler.findIncome();

        TableLayout l1 = (TableLayout)findViewById(R.id.income_data);
        l1.removeAllViews();

        for(String src : incomeList.keySet()){
            TableRow tr =  new TableRow(getBaseContext());

            TextView t1 = new TextView(getBaseContext());
            TextView t2 = new TextView(getBaseContext());

            t1.setText(src);
            t2.setText(incomeList.get(src).toString());

            tr.addView(t1);
            tr.addView(t2);

            l1.addView(tr);
        }
    }

    public void removeIncome () {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteIncome(
                mIncomeSrcDel.getText().toString());

        if (result)
        {
            mIncomeSrcDel.setText("Record Deleted");
        }
        else
            mIncomeSrcDel.setText("No Match Found");
    }
}
