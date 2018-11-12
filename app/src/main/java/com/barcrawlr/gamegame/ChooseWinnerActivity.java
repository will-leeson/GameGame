package com.barcrawlr.gamegame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        byte[] picture = getIntent().getExtras().getByteArray("PicPlayer2");
        if (picture != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            imageView.setImageBitmap(bmp);
        }
        final String word = getIntent().getStringExtra("Word");
        wordGuessed.setText(word);

        submitWinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chooseWinner.isChecked()==true){
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("Word", word);
                    startActivity(intent);

                }
            }


    });
}}
