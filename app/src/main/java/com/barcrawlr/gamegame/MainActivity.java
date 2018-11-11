package com.barcrawlr.gamegame;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Button selectImage;
    private Button play;
    private Context context;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectImage = findViewById(R.id.selectImage);
        play = findViewById(R.id.play);
       // final MainActivity mainActivity =  (MainActivity) this;
        // final SendActivity sendActivity = new SendActivity();


        // RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        //Realm.setDefaultConfiguration(config);
        final Realm realm = Realm.getDefaultInstance();

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PictureActivity.class);
                startActivity(intent);
            }

        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RealmResults<Picture> pictures = realm.where(Picture.class).findAll();
                pictures.first().getImage();
                final Picture pic = pictures.last();
                pic.getImage();


                Intent intent = new Intent(v.getContext(), PlayActivity.class);
                intent.putExtra("Pic", pictures.last().getImage());
                startActivity(intent);
            }
        });
    }
}

