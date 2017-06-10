package com.example.earthshaker.moneybox.categories;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.common.BaseHolderEventBus;

import java.util.List;

/**
 * Created by earthshaker on 14/5/17.
 */

public class SelectCategoryViewHolder extends BaseHolderEventBus {

    private Context context;
    private RecyclerView recycleView;

    public SelectCategoryViewHolder(Context context, View view) {
        this.context = context;
        recycleView = (RecyclerView) view.findViewById(R.id.recycler_view);
        setupData();
    }

    private void setupData() {
        SelectCategoryAdapter selectCategoryAdapter = new SelectCategoryAdapter(
                CategoryUtils.getCategoryConfigs());
        recycleView.setAdapter(selectCategoryAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
        recycleView.setLayoutManager(layoutManager);
        selectCategoryAdapter.setData(CategoryUtils.getCategoryConfigs());
    }

    @Override
    protected void refreshData() {

    }

    @Override
    protected void recreateLayout() {

    }
}
