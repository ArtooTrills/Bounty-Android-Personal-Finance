package bounty.android.example.com.bounty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by RamizMehran on 25/10/2015.
 */
public class IncomeActivity extends Activity{

    private EditText mIncomeSrcAdd;
    private EditText mIncomeAmt;
    private EditText mIncomeSrcDel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_page);

        mIncomeSrcAdd = (EditText) findViewById(R.id.new_income_source);
        mIncomeAmt = (EditText) findViewById(R.id.income_value);

        mIncomeSrcDel = (EditText) findViewById(R.id.old_income_source);
    }

    public void newIncome (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String src =
                mIncomeSrcAdd.getText().toString();

        Income in =
                new Income(src,Double.parseDouble(mIncomeAmt.getText().toString()));

        dbHandler.addIncome(in);
        mIncomeSrcAdd.setText("");
        mIncomeAmt.setText("");
    }

    public void lookupIncome (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        Income product =
                dbHandler.findIncome(mIncomeSrcDel.getText().toString());

        if (product != null) {
            idView.setText(String.valueOf(product.getID()));

            quantityBox.setText(String.valueOf(product.getQuantity()));
        } else {
            idView.setText("No Match Found");
        }
    }

    public void removeProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteProduct(
                productBox.getText().toString());

        if (result)
        {
            idView.setText("Record Deleted");
            productBox.setText("");
            quantityBox.setText("");
        }
        else
            idView.setText("No Match Found");
    }
}
