package com.example.earthshaker.moneybox.categories;

import com.example.earthshaker.moneybox.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthshaker on 14/5/17.
 */

public class CategoryUtils {

    public static List<CategoryConfig> getCategoryConfigs() {

        List<CategoryConfig> categoryConfigList = new ArrayList<>();

        CategoryConfig categoryConfig = new CategoryConfig();

        categoryConfig.setCategoryIcons(R.drawable.exp_food);
        categoryConfig.setCategoryName("Food");
        categoryConfigList.add(categoryConfig);

        categoryConfig.setCategoryIcons(R.drawable.exp_others);
        categoryConfig.setCategoryName("Others");
        categoryConfigList.add(categoryConfig);

        categoryConfig.setCategoryIcons(R.drawable.exp_personal);
        categoryConfig.setCategoryName("Personal");
        categoryConfigList.add(categoryConfig);

        categoryConfig.setCategoryIcons(R.drawable.exp_salon);
        categoryConfig.setCategoryName("Salon");
        categoryConfigList.add(categoryConfig);

        categoryConfig.setCategoryIcons(R.drawable.exp_shopping);
        categoryConfig.setCategoryName("Shopping");
        categoryConfigList.add(categoryConfig);

        categoryConfig.setCategoryIcons(R.drawable.exp_transport);
        categoryConfig.setCategoryName("Transport");
        categoryConfigList.add(categoryConfig);

        return categoryConfigList;
    }
}
