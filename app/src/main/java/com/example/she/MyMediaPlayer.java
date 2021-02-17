package com.example.she;

import android.content.Context;
import android.media.MediaPlayer;

public class MyMediaPlayer {

    static int decidingNumber;
    private static MediaPlayer player = null;
    private static MyMediaPlayer single_inst = null;
    private static MyMediaPlayerInterface playerInterface = null;

    public interface MyMediaPlayerInterface{
        public void onPlayClicked();
        public void onPauseClicked();
    }

    //initialize player
    private MyMediaPlayer(Context context,MyMediaPlayerInterface playerInterface) {
        player = MediaPlayer.create(context, R.raw.policesiren);
        if(playerInterface!=null)
        this.playerInterface = playerInterface;
    }

    //for having only one instance of MyMediaPlayer class
    public static MyMediaPlayer getInstance(Context context, MyMediaPlayerInterface playerInterface) {
        if (single_inst == null) {
            single_inst = new MyMediaPlayer(context,playerInterface);
        }
        return single_inst;
    }

    //play siren
    public void play() {
        if (player != null) {
            player.setLooping(true);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
            player.start();
            if(playerInterface != null)
                playerInterface.onPlayClicked();
        }
        decidingNumber = 1;
    }

    //stop siren
    void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            if(playerInterface != null)
                playerInterface.onPauseClicked();
        }
        single_inst = null;
        decidingNumber = 2;
    }
}