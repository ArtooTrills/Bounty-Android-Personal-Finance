package com.example.earthshaker.moneybox.categories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.earthshaker.moneybox.R;
import com.squareup.picasso.Picasso;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 15/5/17.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    Context context;
    private TextView categoryName;
    private ImageView categoryIcon;
    private LinearLayout selectCategory;

    public CategoryViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        selectCategory = (LinearLayout) itemView.findViewById(R.id.selectCategory);
        categoryName = (TextView) itemView.findViewById(R.id.category_name1);
        categoryIcon = (ImageView) itemView.findViewById(R.id.category_image1);
    }

    public void setViewsData(CategoryConfig categoryConfig) {
        Picasso.with(context)
                .load(com.example.earthshaker.moneybox.common.CategoryUtils.getCategoryIcon(categoryConfig.getCategoryName()))
                .placeholder(R.drawable.background_fill)
                .into(categoryIcon);
        categoryName.setText(categoryConfig.getCategoryName());
        attachListener(categoryConfig);
    }

    private void attachListener(CategoryConfig categoryConfig) {
        selectCategory.setOnClickListener(l -> EventBus.getDefault().post(new CategoryEvent.CategorySelected(categoryConfig.getCategoryName())));
    }
}
