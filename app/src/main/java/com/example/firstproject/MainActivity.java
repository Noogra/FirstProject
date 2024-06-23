package com.example.firstproject;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firstproject.Logic.GameManager;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {


    private MaterialButton main_BTN_left;
    private MaterialButton main_BTN_right;
    private AppCompatImageView[] player;
    private AppCompatImageView[] rocks;
    private AppCompatImageView[] hearts;
    private GameManager gameManager;
    private int playerPosition = 1; // Starts in middle lane

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameManager = new GameManager(this);
        findViews();
        initViews();
        gameManager.startGame();

    }

    private void findViews() {
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);

        rocks = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_rock1),
                findViewById(R.id.main_IMG_rock2),
                findViewById(R.id.main_IMG_rock3),
                findViewById(R.id.main_IMG_rock4),
                findViewById(R.id.main_IMG_rock5),
                findViewById(R.id.main_IMG_rock6),
                findViewById(R.id.main_IMG_rock7),
                findViewById(R.id.main_IMG_rock8),
                findViewById(R.id.main_IMG_rock9),
                findViewById(R.id.main_IMG_rock10),
                findViewById(R.id.main_IMG_rock11),
                findViewById(R.id.main_IMG_rock12),
                findViewById(R.id.main_IMG_rock13),
                findViewById(R.id.main_IMG_rock14),
                findViewById(R.id.main_IMG_rock15),
                findViewById(R.id.main_IMG_rock16),
                findViewById(R.id.main_IMG_rock17),
                findViewById(R.id.main_IMG_rock18),

        };

        player = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_player_left),
                findViewById(R.id.main_IMG_player_center),
                findViewById(R.id.main_IMG_player_right)
        };

        hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };

    }

    private void initViews() {
        for (int i = 0; i < rocks.length; i++) {
            rocks[i].setVisibility(View.INVISIBLE);
        }
        player[0].setVisibility(View.INVISIBLE);
        player[2].setVisibility(View.INVISIBLE);

        main_BTN_right.setOnClickListener(v -> moveRight());
        main_BTN_left.setOnClickListener(v -> moveLeft());
    }

    private void moveRight() {
        int newIndex = gameManager.movePlayerToRight();
        if (newIndex >= 0) {
            player[playerPosition].setVisibility(View.INVISIBLE);
            player[newIndex].setVisibility(View.VISIBLE);
            playerPosition = newIndex;
        }

    }

    private void moveLeft() {
        int newIndex = gameManager.movePlayerToLeft();
        if (newIndex >= 0) {
            player[playerPosition].setVisibility(View.INVISIBLE);
            player[newIndex].setVisibility(View.VISIBLE);
            playerPosition = newIndex;
        }
    }

    public void toastAndVibrate(String text){
        vibrate();
        toast(text);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void refreshUI() {
        if (gameManager.getLife() == 0) {
            gameManager.restartGame();
            for (AppCompatImageView heart : hearts) {
                heart.setVisibility(View.VISIBLE);
            }
        }
        //GAME ON:
        else {
            int[][] gameManagerRockMatrix = gameManager.getRock_matrix();
            for (int i = 0; i < gameManagerRockMatrix.length; i++) {
                for (int j = 0; j < gameManagerRockMatrix[i].length; j++) {
                    int imgNumber = i * gameManagerRockMatrix[i].length + j;
                    if (gameManagerRockMatrix[i][j] == 1) {
                        rocks[imgNumber].setVisibility(AppCompatImageView.VISIBLE);
                    } else {
                        rocks[imgNumber].setVisibility(AppCompatImageView.INVISIBLE);
                    }
                }
            }
            if (gameManager.getNumberOfCrash() != 0) {
                hearts[hearts.length - gameManager.getNumberOfCrash()]
                        .setVisibility(AppCompatImageView.INVISIBLE);
            }
        }
    }
}