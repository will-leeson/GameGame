package com.barcrawlr.gamegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LeaderboardActivity extends AppCompatActivity {

    private LinearLayout players;
    private LinearLayout scores;
    private TextView playerOne;
    private TextView playerOneScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        players = (LinearLayout) findViewById(R.id.leaderboard);
        scores = (LinearLayout) findViewById(R.id.scores);
        playerOne = (TextView) findViewById(R.id.player1);
        playerOneScore = (TextView) findViewById(R.id.player1Score);

        int score = getIntent().getExtras().getInt("Score");
        playerOneScore.setText(String.valueOf(score));

    }
}
