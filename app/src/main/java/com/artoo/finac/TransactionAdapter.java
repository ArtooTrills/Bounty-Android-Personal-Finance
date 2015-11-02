package com.artoo.finac;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TransactionAdapter extends CursorAdapter {

    public TransactionAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.transaction_list_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        TextView textViewValue = (TextView) view.findViewById(R.id.textViewValue);
        TextView textViewType = (TextView) view.findViewById(R.id.textViewType);
        TextView textViewTime = (TextView) view.findViewById(R.id.textViewTime);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("category"));
        float value = cursor.getFloat(cursor.getColumnIndexOrThrow("amount"));
        String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
        String time = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));

        textViewName.setText(name);
        textViewValue.setText("₹ " + String.valueOf(value) + " /-");
        textViewType.setText(type.equals("CR")?"Credit":"Debit");
        textViewTime.setText(time);
    }
}
