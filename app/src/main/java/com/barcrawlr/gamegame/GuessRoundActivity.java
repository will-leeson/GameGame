package com.barcrawlr.gamegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class GuessRoundActivity extends AppCompatActivity {

    EditText myGuess;
    ImageView guessPic;
    Button guessButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_round);

        myGuess = (EditText) findViewById(R.id.guess);
        guessPic = (ImageView) findViewById(R.id.guessImage);
        guessButton = (Button) findViewById(R.id.guessButton);

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
