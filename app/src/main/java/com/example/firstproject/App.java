package com.example.firstproject;

import android.app.Application;

import com.example.firstproject.Models.HighScoreRepository;
import com.example.firstproject.Utilities.SharedPreferencesManagerV3;
import com.example.firstproject.Utilities.SignalManager;


public class App extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        SharedPreferencesManagerV3.init(this);
        SignalManager.init(this);

    }
}
