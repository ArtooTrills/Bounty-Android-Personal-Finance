package com.example.earthshaker.moneybox.categories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.earthshaker.moneybox.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthshaker on 14/5/17.
 */

public class SelectCategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {


    private List<CategoryConfig> mCategoryList;

    SelectCategoryAdapter(List<CategoryConfig> categoryList) {

        mCategoryList = CategoryUtils.getCategoryConfigs();
    }

    @Override public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_select_category_new, parent, false);
        return new CategoryViewHolder(context,view);
    }

    @Override public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryConfig categoryConfig = mCategoryList.get(position);
        holder.setViewsData(categoryConfig);
    }

    @Override public int getItemCount() {
        return mCategoryList.size();
    }

    /**
     * Sets category config list
     *
     * @param categoryList expense category config list
     */
    public void setData(List<CategoryConfig> categoryList) {
        if (categoryList == null) {
            mCategoryList = new ArrayList<>();
        } else {
            mCategoryList = categoryList;
        }
        notifyDataSetChanged();
    }
}
