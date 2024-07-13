package com.example.firstproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firstproject.Fragments.MapFragment;
import com.example.firstproject.Models.HighScoreRepository;
import com.example.firstproject.Models.Score;
import com.example.firstproject.Utilities.SignalManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

public class EndGameActivity extends AppCompatActivity {

    private final int FINE_PERMISSON_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private TextView enter_name_txt;
    private TextView score_txt;
    private EditText input_txt;
    private String name;
    private int scoreNum;
    HighScoreRepository highScoreRepository;
    private MaterialButton main_BTN_menu;
    private MaterialButton main_BTN_restart;
    private MaterialButton submit_BTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        Intent intent = getIntent();
        scoreNum = intent.getIntExtra("score", 0);
        highScoreRepository = HighScoreRepository.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        findViews();
        initViews();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSON_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FINE_PERMISSON_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                SignalManager
                        .getInstance()
                        .toast("Location permission is denied, please allow the permission.");
            }
        }
    }

    private void initViews() {
        main_BTN_menu.setOnClickListener(v -> returnMenu());
        main_BTN_restart.setOnClickListener(v -> returnGame());
        submit_BTN.setOnClickListener(v -> saveGame());
        score_txt.setText(String.valueOf(scoreNum));
    }

    private void returnGame() {
        Intent intent = new Intent(EndGameActivity.this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    private void returnMenu() {
        Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        score_txt = findViewById(R.id.score_txt);
        input_txt = findViewById(R.id.input_txt);
        main_BTN_menu = findViewById(R.id.main_BTN_menu);
        main_BTN_restart = findViewById(R.id.main_BTN_restart);
        submit_BTN = findViewById(R.id.Submit_BTN);
        enter_name_txt = findViewById(R.id.enter_name_txt);
    }
    private void saveGame(){
        if(scoreNum > 0){
            name = input_txt.getText().toString();
            double lat = currentLocation.getLatitude();
            double lon = currentLocation.getLongitude();
            Score score = new Score();
            score.setScore(scoreNum);
            score.setName(name);
            score.setLat(lat);
            score.setLon(lon);
            highScoreRepository.addScore(score);
            SignalManager
                    .getInstance()
                    .toast("Score Saved!");
            input_txt.setVisibility(View.INVISIBLE);
            enter_name_txt.setVisibility(View.INVISIBLE);
            submit_BTN.setVisibility(View.INVISIBLE);
        }
    }
}