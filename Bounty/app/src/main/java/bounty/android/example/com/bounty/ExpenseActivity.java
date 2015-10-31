package bounty.android.example.com.bounty;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by RamizMehran on 25/10/2015.
 */
public class ExpenseActivity extends Activity {

    private EditText mExpenseSrcAdd;
    private EditText mExpenseAmt;
    private EditText mExpenseSrcDel;
    private Switch mExpenseRepeat;
    private Button mAddExpenseSource;
    private Button mDelExpenseSource;
    private TextView mTotalExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_expenses);

        mExpenseSrcAdd = (EditText) findViewById(R.id.new_expense_reason);
        mExpenseAmt = (EditText) findViewById(R.id.new_expense_amount);

        mExpenseSrcDel = (EditText) findViewById(R.id.old_expense_reason);

        mExpenseRepeat = (Switch) findViewById(R.id.is_for_once);

        mTotalExpenses = (TextView) findViewById(R.id.total_expenses);

        mAddExpenseSource = (Button) findViewById(R.id.add_expense);
        mAddExpenseSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mExpenseSrcAdd.getText().toString().matches("")) {

                    Toast.makeText(ExpenseActivity.this, "You did not enter a source", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (mExpenseAmt.getText().toString().matches("")) {

                    Toast.makeText(ExpenseActivity.this, "You did not enter a Amount", Toast.LENGTH_SHORT).show();
                }else {
                    newExpense();
                    updateList();
                }
            }
        });

        mDelExpenseSource = (Button) findViewById(R.id.delete_expense);
        mDelExpenseSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mExpenseSrcDel.getText().toString().matches("")) {

                    Toast.makeText(ExpenseActivity.this, "You did not enter a Source", Toast.LENGTH_SHORT).show();

                }else {
                    removeExpense();
                    updateList();
                }
            }
        });
        updateList();
    }

    public void newExpense() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String src =
                mExpenseSrcAdd.getText().toString();

        Expense in =
                new Expense(src,Double.parseDouble(mExpenseAmt.getText().toString()),mExpenseRepeat.isChecked());

        dbHandler.addExpense(in);
        mExpenseSrcAdd.setText("");
        mExpenseAmt.setText("");
    }

    public void updateList () {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        HashMap<String, Double> ExpenseList = dbHandler.findExpense();

        TableLayout l1 = (TableLayout)findViewById(R.id.expense_data);
        l1.removeAllViews();


        double sum = 0;

        for(String src : ExpenseList.keySet()){
            TableRow tr =  new TableRow(getBaseContext());

            TextView t1 = new TextView(getBaseContext());
            TextView t2 = new TextView(getBaseContext());

            t1.setTextColor(Color.BLACK);
            t2.setTextColor(Color.BLACK);

            t1.setGravity(Gravity.CENTER);
            t2.setGravity(Gravity.CENTER);

            t1.setTextSize(15);
            t2.setTextSize(15);


            t1.setPadding(50, 5, 50, 5);
            t2.setPadding(50, 5, 50, 5);

            t1.setText(src);
            t2.setText(ExpenseList.get(src).toString());
            sum += ExpenseList.get(src);

            tr.addView(t1);
            tr.addView(t2);

            l1.addView(tr);
        }

        mTotalExpenses.setText(""+sum);

        Total_ product = new Total_("input expense", sum);
        dbHandler.addTotal(product);
        Log.d("Total","Expense --> " + product.getSource() + " " + product.getAmount());

    }

    public void removeExpense () {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteExpense(
                mExpenseSrcDel.getText().toString());

        if (result)
        {
            mExpenseSrcDel.setText("Record Deleted");
        }
        else
            mExpenseSrcDel.setText("No Match Found");
    }
}
