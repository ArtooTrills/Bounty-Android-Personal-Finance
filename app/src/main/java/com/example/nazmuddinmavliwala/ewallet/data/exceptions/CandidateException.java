package com.example.nazmuddinmavliwala.ewallet.data.exceptions;

/**
 * Created by ajmac1005 on 20/06/17.
 */

public class CandidateException extends Exception {
    public static final String DEFAULT_MESSAGE="Something went wrong.";
    public CandidateException() {
        super(DEFAULT_MESSAGE);
    }


    public CandidateException(String message) {
        super(message);
    }

    public CandidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CandidateException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public CandidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
