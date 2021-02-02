package com.example.she;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class siren extends AppCompatActivity {

    Button stop, play;
    TextView stopText, playText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siren);

        playText = findViewById(R.id.txt_view3);
        stopText = findViewById(R.id.txt_view4);
        play = findViewById(R.id.b5);
        stop = findViewById(R.id.b6);

        decide(MyMediaPlayer.decidingNumber);
    }

    public void decide(int number){
        if(number == 1){
            play.setVisibility(View.INVISIBLE);
            playText.setVisibility(View.INVISIBLE);

            stop.setVisibility(View.VISIBLE);
            stopText.setVisibility(View.VISIBLE);

        } else{
            stop.setVisibility(View.INVISIBLE);
            stopText.setVisibility(View.INVISIBLE);

            play.setVisibility(View.VISIBLE);
            playText.setVisibility(View.VISIBLE);
        }
    }

    public void play(View view) {
        MyMediaPlayer.getInstance(this).play();
        MyMediaPlayer.decidingNumber = 1;
        decide(MyMediaPlayer.decidingNumber);
    }

    public void stop(View v) {
        MyMediaPlayer.getInstance(this).stopPlayer();
        MyMediaPlayer.decidingNumber = 0;
        decide(MyMediaPlayer.decidingNumber);
    }
}
