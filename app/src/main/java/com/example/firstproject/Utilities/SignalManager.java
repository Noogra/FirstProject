package com.example.firstproject.Utilities;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class SignalManager {
    private static volatile SignalManager instance = null;

    private Context context;
    private static Vibrator vibrator;

    private SignalManager(Context context){ this.context = context; }

    public static SignalManager getInstance(){
        return instance;
    }

    public static SignalManager init(Context context){
        if(instance == null){
            synchronized (SignalManager.class){
                if(instance == null){
                    instance = new SignalManager(context);
                    vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                }
            }
        }
        return getInstance();
    }

    public void toast(String text){
        Toast
                .makeText(context, text, Toast.LENGTH_SHORT)
                .show();
    }

    public void vibrate(long milliseconds){
        vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
    }

}
