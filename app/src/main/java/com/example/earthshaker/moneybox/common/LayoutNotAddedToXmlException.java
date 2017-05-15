package com.example.earthshaker.moneybox.common;

public class LayoutNotAddedToXmlException extends Exception {

    @Override
    public String getMessage() {
        return "You must add no data layout in your xml file.";
    }
}
