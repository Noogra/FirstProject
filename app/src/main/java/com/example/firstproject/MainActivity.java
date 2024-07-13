package com.example.firstproject;

//import static com.example.firstproject.Logic.GameManager.DELAY;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstproject.Utilities.SoundPlayer;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private SeekBar mSeekBar;
    private Button startGameBtn;
    private Button highscoreButton;
    private SoundPlayer soundPlayer;
    private Switch main_SWC_sensor;
    private boolean useSensors = false;
    private long delay = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
    }

    private void initViews() {
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setProgress(2);
        startGameBtn.setOnClickListener(v -> startGameFunc());
        highscoreButton.setOnClickListener(v -> highScoreButtonFunc());
        main_SWC_sensor.setOnClickListener(v -> initSensor());
    }

    private void findViews() {
        mSeekBar = findViewById(R.id.seekBar);
        startGameBtn = findViewById(R.id.startButton);
        highscoreButton = findViewById(R.id.highscoreButton);
        main_SWC_sensor = findViewById(R.id.main_SWC_sensor);
    }
    private void onSwitchClick(View view){
        if(main_SWC_sensor.isChecked()){
            useSensors = true;
        }else
            useSensors = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundPlayer = new SoundPlayer(this);
        //soundPlayer.playBackgroundSound(R.raw.background_sound);
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.stopBackgroundSound();
    }

    private void initSensor() {
        if(!useSensors){
            useSensors = true;
        }else {
            useSensors = false;
        }
    }


    private void highScoreButtonFunc() {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    private void startGameFunc() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("useSensors", useSensors);
        intent.putExtra("delay", delay);
        startActivity(intent);

    }


    /* OnSeekBarChangeListener implementation */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //progress = 0 -> delay = 3000
        //progress = 1 -> delay = 2000
        //progress = 2 -> delay = 1000
        //progress = 3 -> delay = 500
        //progress = 4 -> delay = 250
        switch(progress)
        {
            case 0:
                delay = 3000L;
                break;

            case 1:
                delay = 2000L;
                break;

            case 2:
                delay = 1000L;
                break;

            case 3:
                delay = 500L;
                break;

            case 4:
                delay = 250L;
                break;
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // don't care
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // don't care
    }
}