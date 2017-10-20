package com.example.nazmuddinmavliwala.ewallet.domain;

import com.example.nazmuddinmavliwala.ewallet.data.disc.SharedPrefConstants;
import com.example.nazmuddinmavliwala.ewallet.data.disc.SharedPrefService;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nazmuddinmavliwala on 22/05/17.
 */

@Singleton
public class AuthManager implements Auth {

    private static final String AUTHORIZATION = "Authorization";
    private final SharedPrefService service;

    @Inject
    public AuthManager(SharedPrefService service) {
        this.service = service;
    }

    @Override
    public String getUserId() {
        return this.service.retrieveValue(SharedPrefConstants.USER_ID,"");
    }

    @Override
    public String getCandidateId() {
        return this.service.retrieveValue(SharedPrefConstants.CANDIDATE_ID,"");
    }

    @Override
    public String getAccessToken() {
        return this.service.retrieveValue(SharedPrefConstants.ACCESS_TOKEN,"");
    }

    @Override
    public String getRefreshToken() {
        return this.service.retrieveValue(SharedPrefConstants.REFRESH_TOKEN,"");
    }

    @Override
    public boolean isLoggedIn() {
        return !getAccessToken().isEmpty();
    }

    @Override
    public HashMap<String, String> getAuthMap() {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(AUTHORIZATION, String.format("Bearer %s", getAccessToken()));
        return hashMap;
    }

    @Override
    public boolean isProfiled() {
        return this.service.retrieveValue(SharedPrefConstants.IS_PROFILED,false);
    }
}
