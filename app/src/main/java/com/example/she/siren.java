package com.example.she;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

public class siren extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siren);

    }

    public void play(View view) {
        MyMediaPlayer.getInstance(this).play();
    }

    public void stop(View v) {
        MyMediaPlayer.getInstance(this).stopPlayer();
    }
}
