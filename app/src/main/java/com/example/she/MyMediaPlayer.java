package com.example.she;

import android.content.Context;
import android.media.MediaPlayer;

public class MyMediaPlayer {

    private static MediaPlayer player=null;
    private static MyMediaPlayer single_inst = null;

    //initialize player
    private MyMediaPlayer(Context context){
        player = MediaPlayer.create(context, R.raw.policesiren);
    }

    //for having only one instance of MyMediaPlayer class
    public static MyMediaPlayer getInstance(Context context){
        if(single_inst==null){
            single_inst=new MyMediaPlayer(context);
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
        }
    }

    //stop siren
    void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
        single_inst=null;
    }
}
