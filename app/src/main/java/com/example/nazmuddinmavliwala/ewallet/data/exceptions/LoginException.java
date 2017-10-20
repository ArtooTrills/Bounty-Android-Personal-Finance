package com.example.nazmuddinmavliwala.ewallet.data.exceptions;

/**
 * Created by nazmuddinmavliwala on 13/04/17.
 */

public class LoginException extends Throwable {

    public static LoginException createInstance() {
        return new LoginException();
    }
}
