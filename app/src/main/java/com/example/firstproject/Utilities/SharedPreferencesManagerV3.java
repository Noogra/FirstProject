package com.example.firstproject.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManagerV3 {
    private static volatile SharedPreferencesManagerV3 instance = null;
    SharedPreferences sharedPref;
    private static final String SP_FILE = "SP_FILE";

    private SharedPreferencesManagerV3(Context context){
        sharedPref = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesManagerV3 getInstance() { return instance; }

    public static SharedPreferencesManagerV3 init(Context context){
        if(instance == null){
            synchronized (SharedPreferencesManagerV3.class){
                if(instance == null){
                    instance = new SharedPreferencesManagerV3(context);
                }
            }
        }
        return getInstance();
    }

    public void putString(String key, String value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defValue){
        return sharedPref.getString(key, defValue);
    }

    public void removeEntry(String key){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(key);
        editor.apply();
    }
}
