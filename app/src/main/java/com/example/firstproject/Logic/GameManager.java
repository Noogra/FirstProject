package com.example.firstproject.Logic;

import android.os.Handler;

import com.example.firstproject.GameActivity;
import com.example.firstproject.Models.HighScoreRepository;
import com.example.firstproject.Models.Score;
import com.example.firstproject.Utilities.SignalManager;
import com.example.firstproject.Utilities.SoundPlayer;

import java.util.Random;

public class GameManager {

    private int[][] ghost_matrix = new int[6][5];
    private final int[] player_matrix = new int[5];
    private int player_index = 2;
    GameActivity gameActivity;
    private HighScoreRepository highScoreRepository;
    private long DELAY = 1000L;
    private int life = 3;
    private int score = -6;
    private final int SCORE_POINTS = 1;
    private int numberOfCrash;
    private SoundPlayer soundPlayer;
    private Random random = new Random();
    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run()
        {
            updateLife();
            handler.postDelayed(this, DELAY);
            updateRockMatrix();
            gameActivity.refreshUI();
        }
    };
    public void setDELAY(long newDelay) {DELAY = newDelay; }
    public int getScore() {
        return score;
    }
    public GameManager(GameActivity activity)
    {
        gameActivity = activity;
    }
    public int getLife(){
        return life;
    }
    public int[][] getGhost_matrix(){
        return ghost_matrix;
    }

    private void startThread()
    {
        handler.postDelayed(runnable,0);
    }

    private void initPlayerMatrix(){
        player_matrix[2] = player_index;
    }

    private void initRockMatrix(){
        for (int i = 0; i < ghost_matrix.length; i++) {
            for (int j = 0; j < ghost_matrix[0].length; j++) {
                ghost_matrix[i][j] = 0;
            }
        }
    }

    private void updateRockMatrix(){
        int[][] updatedMatrix = new int[ghost_matrix.length][ghost_matrix[0].length];
        for(int i = 1; i < ghost_matrix.length; i++) {
            for (int j = 0; j < ghost_matrix[0].length; j++) {
                updatedMatrix[i][j] = ghost_matrix[i - 1][j];
                }
            }
        ghost_matrix = updatedMatrix;
        addNewLineToRockMatrix();
        }


    private void addNewLineToRockMatrix(){
        int randomNum = random.nextInt(ghost_matrix[0].length);
        ghost_matrix[0][randomNum] = 1;
    }

    private void updateLife(){
        if(checkCollision()){
            life--;
            numberOfCrash++;
            SignalManager
                    .getInstance()
                    .toast("Crashed!");
            SignalManager
                    .getInstance()
                    .vibrate(1000);
            gameActivity.playHitSound();
        }
        else
            score += SCORE_POINTS;
    }

    public void endGame(){
        handler.removeCallbacks(runnable);
    }

    public boolean checkCollision(){
        if(ghost_matrix[ghost_matrix.length - 1][player_index] == 1){
            return true;
        }
        return false;
    }
    public int movePlayerToRight(){
        if(player_index < player_matrix.length - 1){
            player_matrix[player_index] = 0;
            player_index++;
            player_matrix[player_index] = 1;
            return player_index;
        }
        return -1;
    }

    public int movePlayerToLeft(){
        if(player_index > 0){
            player_matrix[player_index] = 0;
            player_index--;
            player_matrix[player_index] = 1;
            return player_index;
        }
        return -1;
    }

    public int getNumberOfCrash() {
        return numberOfCrash;
    }

    public void initBoard(){
        initRockMatrix();
        initPlayerMatrix();
    }

    public void startGame(){
        initBoard();
        soundPlayer = new SoundPlayer(gameActivity);
        startThread();
    }

    public void restartGame(){
        life = 3;
        numberOfCrash = 0;
        score = 0;
        initBoard();
        gameActivity.refreshUI();
    }
}
