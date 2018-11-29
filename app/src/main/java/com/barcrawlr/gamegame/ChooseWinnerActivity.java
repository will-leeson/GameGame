package com.barcrawlr.gamegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class ChooseWinnerActivity extends AppCompatActivity {
    private CheckBox chooseWinner;
    private Button submitWinner;
    private TextView wordGuessed;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_winner);

        imageView = (ImageView) findViewById(R.id.lastImage);
        chooseWinner = (CheckBox) findViewById(R.id.chooseWinner);
        submitWinner = (Button) findViewById(R.id.submitWinner);
        wordGuessed = (TextView) findViewById(R.id.wordGuessed);

        final Realm realm = Realm.getDefaultInstance();
        final RealmResults<Picture> realmResults = realm.where(Picture.class).findAll();
        byte[] picture = getIntent().getExtras().getByteArray("PicPlayer2");

        commonFunctions.printImage(picture, imageView, false);

        final String word = getIntent().getStringExtra("Word");
        wordGuessed.setText(word);

        submitWinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for when we have implemented three iterations, we will have to check which box was checked...
                //player 1, 2, or 3. With if statements it will send different information via intents to the leaderboardactivity
                //depending on which is sent, the appropriate player will be awarded...
                //we will also send the picture of the drawing that won, maybe? It could be an optioin
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if (chooseWinner.isChecked() == true) {
                            realmResults.last().setScore(+1);
                            Intent intent = new Intent(getBaseContext(), LeaderboardActivity.class);
                           // intent.putExtra("Word", word);
                            intent.putExtra("Score", realmResults.last().getScore());
                            realm.copyToRealm(realmResults);
                            finish();
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}
