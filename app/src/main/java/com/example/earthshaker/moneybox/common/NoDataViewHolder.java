package com.example.earthshaker.moneybox.common;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.earthshaker.moneybox.R;


public class NoDataViewHolder {
    private LinearLayout noDataLayout;
    private TextView mainLine, purposeLine, actionLine;

    public NoDataViewHolder(View parentView, String mainLineValue, String purposeLineValue,
                            String actionLineValue) throws LayoutNotAddedToXmlException {
        noDataLayout = (LinearLayout) parentView.findViewById(R.id.no_data_layout);
        if (noDataLayout == null) {
            throw new LayoutNotAddedToXmlException();
        }
        mainLine = (TextView) noDataLayout.findViewById(R.id.main_line);
        purposeLine = (TextView) noDataLayout.findViewById(R.id.type_of_data);
        actionLine = (TextView) noDataLayout.findViewById(R.id.action_perform);
        setData(mainLineValue, purposeLineValue, actionLineValue);
    }

    public void setNewTexts(String mainLineValue, String purposeLineValue, String actionLineValue) {
        setData(mainLineValue, purposeLineValue, actionLineValue);
    }

    public void hideNoDataLayout() {
        noDataLayout.setVisibility(View.GONE);
    }

    public void showNoDataLayout() {
        noDataLayout.setVisibility(View.VISIBLE);
    }

    public View getNoDataLayoutView() {
        return noDataLayout;
    }

    private void setData(String mainLineValue, String purposeLineValue, String actionLineValue) {
        mainLine.setText(mainLineValue);
        purposeLine.setText(purposeLineValue);
        actionLine.setText(actionLineValue);
    }
}
