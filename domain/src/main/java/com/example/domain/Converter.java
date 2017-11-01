package com.example.domain;

/**
 * Created by nazmuddinmavliwala on 29/10/2017.
 */

public interface Converter<FROM,TO> {

    public TO convert(FROM data);
}
