package com.barcrawlr.gamegame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import io.realm.Realm;
import io.realm.RealmResults;

public class PlayActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText word;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        imageView = (ImageView) findViewById(R.id.targetimage);
        word = (EditText) findViewById(R.id.enterWord);
        submit = (Button) findViewById(R.id.submit);
       final Realm realm = Realm.getDefaultInstance();
        byte[] picture = getIntent().getExtras().getByteArray("Pic");
        final RealmResults<Picture> pictures = realm.where(Picture.class).findAll();

        if (picture != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            imageView.setImageBitmap(bmp);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                com.barcrawlr.gamegame.Picture pictureObject = new com.barcrawlr.gamegame.Picture();

                pictures.last().setWord(word.toString());
                Intent intent = new Intent(getBaseContext(), PlayActivity.class);
                intent.putExtra("Word", pictures.last().getWord());
                intent.putExtra("PicPlayer2",pictures.last().getImage());
                realm.copyToRealm(pictureObject);
                finish();
            }
        });
    }  });
    }
}
