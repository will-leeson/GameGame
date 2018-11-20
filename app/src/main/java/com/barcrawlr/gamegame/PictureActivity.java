package com.barcrawlr.gamegame;

import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Text;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.Random;

public class PictureActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private Realm realm;
    TextView textTargetUri;
    private Button buttonLoadImage;
    private Bitmap bitmap;
    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        realm = Realm.getDefaultInstance();
        imageButton = findViewById(R.id.imageButton);
        buttonLoadImage = findViewById(R.id.loadimage);
        textTargetUri = (TextView) findViewById(R.id.targeturi);
        final Random rand = new Random();

        buttonLoadImage.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        PictureActivity.super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 || requestCode == 0 && resultCode == RESULT_OK) {

            final int gameIdGenerator = rand.nextInt(1049231) + 1;
            if (requestCode == 1) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

               imageButton.setImageBitmap(imageBitmap);
            } else if (requestCode == 0) {
                final Uri targetUri = data.getData();
                textTargetUri.setText(targetUri.toString());
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));

                } catch (FileNotFoundException e) { }
            }

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    com.barcrawlr.gamegame.Picture picture = new com.barcrawlr.gamegame.Picture();
                    final RealmResults<User> users = realm.where(User.class).findAll();
                    final RealmResults<Game> newGame = realm.where(Game.class).findAll();


                    Game newGameId = new Game();
                    if (requestCode == 1) {
                       BitmapDrawable image = (BitmapDrawable) imageButton.getDrawable();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageInByte = baos.toByteArray();
                        picture.setImage(imageInByte);
                        while(!newGame.contains(gameIdGenerator) ){
                            newGameId.setId(gameIdGenerator);
                            if (!newGame.contains(gameIdGenerator)){
                                break;
                            }
                        }
                        //user.setId(242341);
                      //  realm.copyFromRealm(user);
                        picture.setPicId();
                        realm.copyToRealm(newGameId);
                        realm.copyToRealm(picture);
                        finish();
                    } else if (requestCode == 0) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        //bitmap.recycle();
                        picture.setImage(byteArray);


                        while(!newGame.contains(gameIdGenerator) ){
                            newGameId.setId(gameIdGenerator);
                            if (!newGame.contains(gameIdGenerator)){
                                break;
                            }
                        }

                        picture.setPicId();

                        realm.copyToRealm(newGameId);
                        realm.copyToRealm(picture);
                        finish();
                       /** Intent intent = new Intent(getBaseContext(), TestRetriv.class);
                        intent.putExtra("Pic", picture.getImage());
                        startActivity(intent);
                        **/
                    }
                }
            });
        }
    }
}




