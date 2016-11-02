package com.example.soujanyaponnapalli.bounty;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by soujanyaponnapalli on 29/10/16.
 */
public class Transactions extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.transactionslayout, container, false);

        //Creating a parseQuery object - dataBase is named "Expenses"

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Expenses");
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        String s = pref.getString("username", "test");

        query.whereEqualTo("user_id",s);
        List<ParseObject> objects = null;
        try {
            objects = query.find();
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        ArrayList<String> items = new ArrayList<String>();

        // Concatenating the information in the DB

        for(int i = 0; i < objects.size(); i++){
            ParseObject entry = objects.get(i);
            String towards = entry.getString("Towards");
            int expense = (int)entry.getNumber("Expense");
            items.add(i,towards.concat("-").concat(Integer.toString(expense)));
        }

        // Creating an adapter with the items retrieved from the DataBase
        // activity_list_view - View corresponding to the display of the List Of Items
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_listview, items);

        // ListViewTransactions - individual element Layout
        ListView lv = (ListView) v.findViewById(R.id.listViewTransactions);
        lv.setAdapter(adapter);

        return v;
    }
}
