package com.example.nazmuddinmavliwala.ewallet.domain;

import java.util.HashMap;

/**
 * Created by nazmuddinmavliwala on 14/04/17.
 */

public interface Auth {

    String getUserId();

    String getCandidateId();

    String getAccessToken();

    String getRefreshToken();

    boolean isLoggedIn();

    HashMap<String,String> getAuthMap();

    boolean isProfiled();
}
