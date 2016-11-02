package com.example.soujanyaponnapalli.bounty;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by soujanyaponnapalli on 29/10/16.
 */
public class HomeFragment extends Fragment {

    View v1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v1 = inflater.inflate(R.layout.homelayout,container,false);
        Button b = (Button) v1.findViewById(R.id.update);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        final String s = pref.getString("username", "test");


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri inboxURI = Uri.parse("content://sms/inbox");
                String[] reqCols = new String[] { "_id", "address", "body" };
                /*
                Getting a cursor to access messages SMS
                 */
                ContentResolver cr = getActivity().getContentResolver();
                Cursor cursor = cr.query(inboxURI, reqCols, null, null, null);

                if (cursor.moveToFirst()) { // must check the result to prevent exception when no messages
                    do {
                        String msgData = cursor.getString(2); // Body of the message
                        String copy = msgData; // preserve the original message to insert into db

                        // check if db contains the message already
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Expenses");
                        query.whereEqualTo("user_id",s);
                        query.whereEqualTo("Towards", copy);
                        try {
                            List<ParseObject> check = query.find();
                            if(check.size() > 0){
                                continue;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // get the amount from the message
                        // simplest regex matching is used

                        Log.d("msgs", msgData);
                        String[] list = msgData.split("Rs.");
                        if(list.length > 1){
                            String price = list[1].split(" ")[0];

                            ParseObject dbEntry = new ParseObject("Expenses");
                            SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
                            String s = pref.getString("username","test");
                            dbEntry.put("user_id", s);
                            dbEntry.put("Towards", copy);
                            dbEntry.put("Date", "");
                            dbEntry.put("Expense", Integer.parseInt(price));
                            dbEntry.saveInBackground();
                            // inserted an entry into the db
                        }

                        // use msgData
                    } while (cursor.moveToNext());
                } else {

                }

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Expenses");

                // getting all the transactions corresponding to a user
                query.whereEqualTo("user_id",s);
                List<ParseObject> objects = null;
                try {
                    objects = query.find();
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

                // calculating the cumulative expense
                ArrayList<String> items = new ArrayList<String>();
                int cumulativeExpenses = 0;
                for(int i = 0; i < objects.size(); i++){
                    ParseObject entry = objects.get(i);
                    int expense = (int)entry.getNumber("Expense");
                    cumulativeExpenses = cumulativeExpenses + expense;
                }

                // Setting the Cumulative expenses text field
                TextView textView = (TextView) v1.findViewById(R.id.cumulativeExpense);
                textView.setText("Cumulative Expenses : " + cumulativeExpenses);
            }

        });
        return v1;
    }
}
