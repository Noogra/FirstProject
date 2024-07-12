package com.example.firstproject.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.example.firstproject.Utilities.SharedPreferencesManagerV3;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HighScoreRepository {
    public static HighScoreRepository instance;
    public static final String HIGH_SCORES = "highScores";
    private ArrayList<Score> scores;
    private final int MAX_SCORES = 10;

    private HighScoreRepository(){
        String json = SharedPreferencesManagerV3
                .getInstance()
                .getString(HIGH_SCORES, "");
        if(json.equals("")){
            scores = new ArrayList<>();
        }else{
            scores = new Gson().fromJson(json, new TypeToken<ArrayList<Score>>(){
            }.getType());
        }
        saveScores();
    }

    private void saveScores(){
        String json = new Gson().toJson(scores);
        SharedPreferencesManagerV3
                .getInstance()
                .putString(HIGH_SCORES, json);
    }

    public static HighScoreRepository getInstance() {
        if(instance == null){
            instance = new HighScoreRepository();
        }
        return instance;
    }

    public void addScore(Score score){
        scores.add(score);
        Collections.sort(scores, new Comparator<Score>() {
            @Override
            public int compare(Score s1, Score s2) {
                return Integer.compare(s2.getScore(), s1.getScore());
            }
        });
        if(scores.size() > MAX_SCORES){
            scores.remove(scores.size() - 1);
        }
    }

}

