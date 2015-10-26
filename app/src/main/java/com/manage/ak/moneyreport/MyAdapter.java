package com.manage.ak.moneyreport;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

// Adapter to connect row.xml with the recycler view
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    List<Sms> smsList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView smsType;
        public TextView smsAmt;
        public TextView smsDate;
        public TextView smsBal;
        public ImageView typeImage;

        public ViewHolder(View v) {
            super(v);

            typeImage = (ImageView) v.findViewById(R.id.typeImage);
            smsType = (TextView) v.findViewById(R.id.smsType);
            smsAmt = (TextView) v.findViewById(R.id.smsAmt);
            smsDate = (TextView) v.findViewById(R.id.smsDate);
            smsBal = (TextView) v.findViewById(R.id.smsBal);
        }
    }

    public MyAdapter(List<Sms> smsList, Context context) {
        this.smsList = smsList;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String t = smsList.get(position).getMsgType();
        holder.smsType.setText(t);
        if (t.equals("Personal Expenses") || t.equals("Food") || t.equals("Transport")) {
            holder.smsAmt.setTextColor(Color.RED);
            holder.typeImage.setBackgroundResource(R.drawable.ic_action_arrow_top);

        } else {
            holder.smsAmt.setTextColor(Color.parseColor("#b79be344"));
            holder.typeImage.setBackgroundResource(R.drawable.ic_action_arrow_bottom);
        }
        holder.smsAmt.setText("₹ " + smsList.get(position).getMsgAmt());
        holder.smsDate.setText(smsList.get(position).getFormatDate());
        holder.smsBal.setText("₹ " + smsList.get(position).getMsgBal());
        if (Double.parseDouble(smsList.get(position).getMsgBal()) < 0.0)
            holder.smsBal.setTextColor(Color.RED);
        else
            holder.smsBal.setTextColor(Color.parseColor("#b79be344"));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

}
