package com.example.she;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver{

    final String TAG = "SIREN_NOTIFY";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("pause_clicked")) { //PAUSE clicked
            MyMediaPlayer.getInstance(context,null).stopPlayer();
            Log.d(TAG, "ON PAUSE ACTION");

        }else if(intent.getAction().equals("play_clicked")){ //PLAY clicked
            MyMediaPlayer.getInstance(context,null).play();
            Log.d(TAG, "ON PLAY ACTION");

        } else {
            Log.d(TAG, "Something went wrong");
        }
    }
}