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
        final RealmResults<User> user = realm.where(User.class).findAll();
        //String sendingUser = user.get(0).getId();
        final RealmResults<Picture> pictures = realm.where(Picture.class).findAll();
       // pictures.get(sendingUser)
        byte[] picture = getIntent().getExtras().getByteArray("Pic");
        //void sendingUser = pictures.get(user.get(0).getId()).setId(user.get(0).getId());

        commonFunctions.printImage(picture, imageView);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User user = new User();
                realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                pictures.last().setWord(word.getText().toString());
             //   user.setId("asdfasf");
                //pictures.last().setId();
                Intent intent = new Intent(getBaseContext(), ChooseWinnerActivity.class);
                intent.putExtra("Word", pictures.get(user.getId()).getWord());
                //The line under this comment is a place holder. We will need three iterations depending on the player/the players turn
                //what I am testing now if the word can be sent to the first player on a page where they can then vote who wins
                intent.putExtra("PicPlayer2", pictures.get(user.getId()).getImage());
                realm.copyToRealm(pictures);
                finish();
                startActivity(intent);
            }
        });
    }  });
    }
}