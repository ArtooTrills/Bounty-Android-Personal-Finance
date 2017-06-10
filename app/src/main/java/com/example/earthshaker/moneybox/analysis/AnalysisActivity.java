package com.example.earthshaker.moneybox.analysis;

import android.os.Bundle;
import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.common.BaseActivity;

/**
 * Created by earthshaker on 15/5/17.
 */

public class AnalysisActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI(R.layout.activity_analysis,R.id.chartLayout);

    }

    @Override
    protected void setupViewHolder(View view) {
        new AnalysisViewHolder(this,view);
    }
}
