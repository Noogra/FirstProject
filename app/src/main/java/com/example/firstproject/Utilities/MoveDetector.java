package com.example.firstproject.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.firstproject.Interface.MoveCallback;

public class MoveDetector {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    private int moveCountZ = 0;

    private MoveCallback moveCallback;
    private long timestamp = 0l;

    public MoveDetector(Context context, MoveCallback moveCallback){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.moveCallback = moveCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float z = event.values[0];
                calculateMove(z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    private void calculateMove(float z) {
        if(System.currentTimeMillis() - timestamp > 500){
            timestamp = System.currentTimeMillis();
            if(z > 0.0){
                moveCountZ++;
                if(moveCallback != null){
                    moveCallback.moveToLeft();
                }
            }
            if(z < 0.0){
                moveCountZ--;
                if(moveCallback != null){
                    moveCallback.moveToRight();
                }

            }
        }
    }
    public void start(){
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );

    }
    public void stop(){
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }
}
