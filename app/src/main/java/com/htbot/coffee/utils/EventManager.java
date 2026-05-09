package com.htbot.coffee.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EventManager {
    private static EventManager instance;
    private final MutableLiveData<Boolean> tokenExpired = new MutableLiveData<>();

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }

        return instance;
    }

    public LiveData<Boolean> getTokenExpiredLiveData() {
        return tokenExpired;
    }

    public void postTokenExpired() {
        tokenExpired.postValue(true);
    }

    public void resetTokenExpired() {
        tokenExpired.postValue(false);
    }
}
