package com.example.soujanyaponnapalli.bounty;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseObject;


public class AddExpenses extends Fragment {

    View v;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.addexpenseslayout,container,false);

        /*
        On clicking the button, the info in the fields should be entered.
        A function called insertEntry is written which is called on button click
         */

        Button b = (Button)v.findViewById(R.id.addExpense);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               insertEntry();
            }
        });

        return v;
    }

    public void insertEntry(){
        /*
        Getting to access the fields in the view
         */
        EditText towards = (EditText)v.findViewById(R.id.towards);
        EditText date = (EditText)v.findViewById(R.id.date);
        EditText expenditure = (EditText)v.findViewById(R.id.expenditure);

        /*
        Getting the text or the information in the fields
         */
        String cause = towards.getText().toString();
        String dateString = date.getText().toString();
        String price = expenditure.getText().toString();


        /*
        Creating a database entry and inserting into the database
        Database used : Parse

        The information is stored against the userId or the username helping us to distinguish
        between users and their expenses

        Creating a parseObject, getting the username, and inserting into db
        */
        ParseObject dbEntry = new ParseObject("Expenses");
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        String s = pref.getString("username","test");
        dbEntry.put("user_id", s);
        dbEntry.put("Towards", cause);
        dbEntry.put("Date", dateString);
        dbEntry.put("Expense", Integer.parseInt(price));
        dbEntry.saveInBackground();

        /*
        Making the fields null, once the entry is made into the db
         */
        towards.setText("");
        date.setText("");
        expenditure.setText("");
    }
}
