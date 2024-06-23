package com.example.firstproject.Logic;

import android.os.Handler;
import android.widget.Toast;

import com.example.firstproject.MainActivity;

import java.util.Random;

public class GameManager {

    private int[][] rock_matrix = new int[6][3];
    private final int[] player_matrix = new int[3];
    private int player_index = 1;
    MainActivity mainActivity;
    private static final long DELAY = 1000L;
    private int life = 3;
    private int numberOfCrash;
    private Random random = new Random();
    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run()
        {
            updateLife();
            handler.postDelayed(this, DELAY);
            updateRockMatrix();
            mainActivity.refreshUI();
        }
    };

    public GameManager(MainActivity activity)
    {
        mainActivity = activity;
    }
    public int getLife(){
        return life;
    }
    public int[][] getRock_matrix(){
        return rock_matrix;
    }

    private void startThread()
    {
        handler.postDelayed(runnable,0);
    }

    private void initPlayerMatrix(){
        player_matrix[1] = player_index;
    }

    private void initRockMatrix(){
        for (int i = 0; i < rock_matrix.length; i++) {
            for (int j = 0; j < rock_matrix[0].length; j++) {
                rock_matrix[i][j] = 0;
            }
        }
    }

    private void updateRockMatrix(){
        int[][] updatedMatrix = new int[rock_matrix.length][rock_matrix[0].length];
        for(int i = 1; i < rock_matrix.length; i++) {
            for (int j = 0; j < rock_matrix[0].length; j++) {
                updatedMatrix[i][j] = rock_matrix[i - 1][j];
            }
        }
        rock_matrix = updatedMatrix;
        addNewLineToRockMatrix();
    }

    private void addNewLineToRockMatrix(){
        int randomNum = random.nextInt(3);
        rock_matrix[0][randomNum] = 1;
    }

    private void updateLife(){
        if(rock_matrix[rock_matrix.length - 1][player_index] == 1){
            life--;
            numberOfCrash++;
            mainActivity.toastAndVibrate("Crashed!");
        }
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
        startThread();
    }

    public void restartGame(){
        life = 3;
        numberOfCrash = 0;
        initBoard();
        mainActivity.refreshUI();
    }
}
