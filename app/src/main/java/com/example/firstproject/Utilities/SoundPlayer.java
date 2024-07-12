package com.example.firstproject.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SoundPlayer {

    private Context context;
    private Executor executor;
    private MediaPlayer backgroundPlayer;
    private MediaPlayer hitSoundPlayer;
    private MediaPlayer gameOverSoundplayer;
    private int backgroundPausePosition;

    public SoundPlayer(Context context) {
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void playBackgroundSound(int resID){
        if(backgroundPlayer == null){
            executor.execute(()->{
                backgroundPlayer = MediaPlayer.create(context, resID);
                backgroundPlayer.setLooping(true);
                backgroundPlayer.setVolume(1.0f, 1.0f);
                backgroundPlayer.start();
            });
        }

    }

    public void stopBackgroundSound(){
        if(backgroundPlayer != null){
            executor.execute(()->{
                backgroundPlayer.stop();
                backgroundPlayer.release();
                backgroundPlayer = null;
            });
        }
    }

    public void pauseBackgroundSound(){
        if(backgroundPlayer != null && backgroundPlayer.isPlaying()){
            executor.execute(()->{
                backgroundPausePosition = backgroundPlayer.getCurrentPosition();
                backgroundPlayer.pause();
            });
        }
    }

    public void resumeBackgroundSound(){
        if(backgroundPlayer != null){
            executor.execute(()->{
                backgroundPlayer.seekTo(backgroundPausePosition);
                backgroundPlayer.start();
            });
        }
    }

    public void playHitSound(int resID){
        if(hitSoundPlayer == null){
            executor.execute(()->{
                hitSoundPlayer = MediaPlayer.create(context, resID);
                hitSoundPlayer.setLooping(false);
                hitSoundPlayer.setVolume(1.0f, 1.0f);
                hitSoundPlayer.start();

                //wait till sound ends
                hitSoundPlayer.setOnCompletionListener(mp -> {
                    hitSoundPlayer.release();
                    hitSoundPlayer = null;
                    resumeBackgroundSound(); //resume background
                });
            });
        }
    }

    public void stopHitSound(){
        if(hitSoundPlayer != null){
            executor.execute(()->{
                hitSoundPlayer.stop();
                hitSoundPlayer.release();
                hitSoundPlayer = null;
            });
        }
    }

    public void playGameOverSound(int resID){
        if(gameOverSoundplayer == null){
            executor.execute(()-> {
                gameOverSoundplayer = MediaPlayer.create(context, resID);
                gameOverSoundplayer.setLooping(false);
                gameOverSoundplayer.setVolume(2.0f, 2.0f);
                gameOverSoundplayer.start();

                gameOverSoundplayer.setOnCompletionListener(mp -> {
                    gameOverSoundplayer.release();
                    gameOverSoundplayer = null;
                });

            });
        }
    }

    public void stopGameOverSound(){
        if(gameOverSoundplayer != null){
            executor.execute(()->{
                gameOverSoundplayer.stop();
                gameOverSoundplayer.release();
                gameOverSoundplayer = null;
            });
        }
    }

}
