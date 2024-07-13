package com.example.firstproject;

import android.app.Application;

import com.example.firstproject.Models.HighScoreRepository;
import com.example.firstproject.Utilities.SharedPreferencesManagerV3;
import com.example.firstproject.Utilities.SignalManager;


public class App extends Application {
    //SharedPreferencesManagerV3 sharedPreferencesManagerV3;
    @Override
    public void onCreate(){
        super.onCreate();
        SharedPreferencesManagerV3.init(this);
        /*sharedPreferencesManagerV3 = SharedPreferencesManagerV3.getInstance();
        sharedPreferencesManagerV3.removeEntry("highScores");*/
        SignalManager.init(this);

    }
}
