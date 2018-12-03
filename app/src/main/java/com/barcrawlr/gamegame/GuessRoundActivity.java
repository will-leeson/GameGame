package com.barcrawlr.gamegame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import io.realm.Realm;
import io.realm.Sort;
import io.realm.SyncUser;

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

        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.bg);
        guessPic.setImageBitmap(bmp);

        Realm realm = Realm.getDefaultInstance();
        //realm.insertOrUpdate(Picture.getId().g);

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RoundResults.class);
                startActivity(intent);
            }
        });

        /*Realm realm = Realm.getDefaultInstance();
        if ((realm.where(User.class).findAll().last().equals(SyncUser.currentUser())))
        {
            guessButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), RoundResults.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            guessButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }*/
    }
}
