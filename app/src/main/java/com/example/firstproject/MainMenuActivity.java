package com.example.firstproject;

import static com.example.firstproject.Logic.GameManager.DELAY;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.SwitchCompat;

import com.example.firstproject.Logic.GameManager;
import com.example.firstproject.Utilities.SoundPlayer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainMenuActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private SeekBar mSeekBar;
    private Button startGameBtn;
    private Button highscoreButton;
    private SoundPlayer soundPlayer;
    private Switch main_SWC_sensor;
    private boolean useSensors = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("useSensors", useSensors);
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
                DELAY = 3000;
                break;

            case 1:
                DELAY = 2000;
                break;

            case 2:
                DELAY = 1000;
                break;

            case 3:
                DELAY = 500;
                break;

            case 4:
                DELAY = 250;
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