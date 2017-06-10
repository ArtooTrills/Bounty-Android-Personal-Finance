package com.example.earthshaker.moneybox.categories;

/**
 * Created by earthshaker on 15/5/17.
 */

public class CategoryEvent {

    public static class CategorySelected{
        private String category;

        public String getCategory() {
            return category;
        }

        public CategorySelected(String category) {

            this.category = category;
        }
    }

    public static class FinishACtivity{}
}
