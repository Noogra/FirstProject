package com.example.firstproject;

import static com.example.firstproject.Logic.GameManager.DELAY;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.firstproject.Interface.MoveCallback;
import com.example.firstproject.Logic.GameManager;
import com.example.firstproject.Utilities.MoveDetector;
import com.example.firstproject.Utilities.SoundPlayer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {



    private MaterialButton main_BTN_left;
    private MaterialButton main_BTN_right;
    private AppCompatImageView[] player;
    private AppCompatImageView[] ghosts;
    private AppCompatImageView[] hearts;
    private MaterialTextView main_LBL_score;
    private SoundPlayer soundPlayer;
    private GameManager gameManager;
    private MoveDetector moveDetector;
    public boolean useSensors;
    private int playerPosition = 2; // Starts in middle lane

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        useSensors = intent.getBooleanExtra("useSensors",false);
        initMoveDetector();
        gameManager = new GameManager(this);
        findViews();
        initViews();
        gameManager.startGame();

    }

    @Override
    protected void onResume() {
        super.onResume();
        soundPlayer = new SoundPlayer(this);
        soundPlayer.playBackgroundSound(R.raw.background_sound);
        if(useSensors){
            moveDetector.start();
            main_BTN_left.setVisibility(View.INVISIBLE);
            main_BTN_right.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.stopBackgroundSound();
        if(useSensors){
            moveDetector.stop();
        }
    }

    private void findViews() {
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_LBL_score = findViewById(R.id.main_LBL_score);

        ghosts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_ghost1),
                findViewById(R.id.main_IMG_ghost2),
                findViewById(R.id.main_IMG_ghost3),
                findViewById(R.id.main_IMG_ghost4),
                findViewById(R.id.main_IMG_ghost5),
                findViewById(R.id.main_IMG_ghost6),
                findViewById(R.id.main_IMG_ghost7),
                findViewById(R.id.main_IMG_ghost8),
                findViewById(R.id.main_IMG_ghost9),
                findViewById(R.id.main_IMG_ghost10),
                findViewById(R.id.main_IMG_ghost11),
                findViewById(R.id.main_IMG_ghost12),
                findViewById(R.id.main_IMG_ghost13),
                findViewById(R.id.main_IMG_ghost14),
                findViewById(R.id.main_IMG_ghost15),
                findViewById(R.id.main_IMG_ghost16),
                findViewById(R.id.main_IMG_ghost17),
                findViewById(R.id.main_IMG_ghost18),
                findViewById(R.id.main_IMG_ghost19),
                findViewById(R.id.main_IMG_ghost20),
                findViewById(R.id.main_IMG_ghost21),
                findViewById(R.id.main_IMG_ghost22),
                findViewById(R.id.main_IMG_ghost23),
                findViewById(R.id.main_IMG_ghost24),
                findViewById(R.id.main_IMG_ghost25),
                findViewById(R.id.main_IMG_ghost26),
                findViewById(R.id.main_IMG_ghost27),
                findViewById(R.id.main_IMG_ghost28),
                findViewById(R.id.main_IMG_ghost29),
                findViewById(R.id.main_IMG_ghost30),

        };

        player = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_player_left),
                findViewById(R.id.main_IMG_player_left_center),
                findViewById(R.id.main_IMG_player_center),
                findViewById(R.id.main_IMG_player_right_center),
                findViewById(R.id.main_IMG_player_right)
        };

        hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };

    }

    private void initViews() {
        for (int i = 0; i < ghosts.length; i++) {
            ghosts[i].setVisibility(View.INVISIBLE);
        }
        for(int i = 0; i < player.length; i++){
            if(i != player.length/2){
                player[i].setVisibility(View.INVISIBLE);
            }
        }
        main_LBL_score.setText(String.valueOf(gameManager));
        main_BTN_right.setOnClickListener(v -> moveRight());
        main_BTN_left.setOnClickListener(v -> moveLeft());
    }

    private void initMoveDetector(){
        moveDetector = new MoveDetector(this,
                new MoveCallback() {
                    @Override
                    public void moveToRight() {
                        moveRight();
                    }

                    @Override
                    public void moveToLeft() {
                        moveLeft();

                    }
                }
        );
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
    public void playHitSound(){
            soundPlayer.pauseBackgroundSound();
            soundPlayer.playHitSound(R.raw.hit_sound);
            soundPlayer.stopHitSound();
    }

    public void refreshUI() {
        if (gameManager.getLife() == 0) {
            soundPlayer.stopGameOverSound();
            soundPlayer.stopHitSound();
            soundPlayer.playGameOverSound(R.raw.game_over_sound);
            gameManager.restartGame();
            soundPlayer.stopGameOverSound();
            for (AppCompatImageView heart : hearts) {
                heart.setVisibility(View.VISIBLE);
            }
        }
        //GAME ON:
        else {
            int[][] gameManagerRockMatrix = gameManager.getGhost_matrix();
            for (int i = 0; i < gameManagerRockMatrix.length; i++) {
                for (int j = 0; j < gameManagerRockMatrix[i].length; j++) {
                    int imgNumber = i * gameManagerRockMatrix[i].length + j;
                    if (gameManagerRockMatrix[i][j] == 1) {
                        ghosts[imgNumber].setVisibility(AppCompatImageView.VISIBLE);
                    } else {
                        ghosts[imgNumber].setVisibility(AppCompatImageView.INVISIBLE);
                    }
                }
            }
            if (gameManager.getNumberOfCrash() != 0) {
                hearts[hearts.length - gameManager.getNumberOfCrash()]
                        .setVisibility(AppCompatImageView.INVISIBLE);
            }
            if(gameManager.getScore() > 0)
                main_LBL_score.setText(String.valueOf(gameManager.getScore()));
            else
                main_LBL_score.setText(String.valueOf(0));
        }
    }
}