package com.example.nazmuddinmavliwala.ewallet.data.exceptions;

/**
 * Created by nazmuddinmavliwala on 26/04/17.
 */

public class NetworkException extends Throwable {

    public static NetworkException createInstance() {
        return new NetworkException();
    }
}
