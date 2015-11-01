package com.artoo.finac;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class DetailViewAdapter extends CursorAdapter {

    public DetailViewAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.detail_list_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        TextView textViewValue = (TextView) view.findViewById(R.id.textViewValue);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("category"));
        float value = cursor.getFloat(cursor.getColumnIndexOrThrow("amount"));

        textViewName.setText(name);
        textViewValue.setText("₹ " + String.valueOf(value) + " /-");
    }
}