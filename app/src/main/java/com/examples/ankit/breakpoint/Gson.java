package com.examples.ankit.breakpoint;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

public class Gson {
    private static com.google.gson.Gson instance =
            new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .disableHtmlEscaping()
                    .create();

    private Gson() {
    }

    public static com.google.gson.Gson getInstance() {
        return instance;
    }
}