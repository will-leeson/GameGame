package com.barcrawlr.gamegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.Realm;

public class LeaderboardActivity extends AppCompatActivity {

    private LinearLayout players;
    private LinearLayout scores;
    private TextView playerOne;
    private TextView playerOneScore;
    Picture pic;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        players = (LinearLayout) findViewById(R.id.leaderboard);
        scores = (LinearLayout) findViewById(R.id.scores);
        playerOne = (TextView) findViewById(R.id.player1);
        playerOneScore = (TextView) findViewById(R.id.player1Score);

        String id2 = (String) getIntent().getStringExtra("Pic");

        Realm realm = Realm.getDefaultInstance();

        pic = realm.where(Picture.class).equalTo("id",id2).findFirst();

        playerOne.setText("Username Here");
        playerOneScore.setText(String.valueOf(pic.getScore()));

    }
}
