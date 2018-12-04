package com.barcrawlr.gamegame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import io.realm.Realm;

public class PictureActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private Realm realm;
    TextView textTargetUri;
    private Button buttonLoadImage;
    private Bitmap bitmap;
    private Random rand = new Random();
    private String imageFilePath;
    private int gameIdGenerator;
    private File photoFile;
    private Uri photoURI;

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
                    photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) { }

                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(view.getContext(),"com.barcrawlr.gamegame.provider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, 1);
                    }
                }
            }
        });
    }
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        PictureActivity.super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 || requestCode == 0 && resultCode == RESULT_OK) {

            gameIdGenerator = rand.nextInt(99999) + 1;
            if (requestCode == 1) {

                Uri targetUri = photoURI;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


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
                    Game newGameId = new Game();
                    if (requestCode == 1) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageInByte = baos.toByteArray();
                        picture.setImage(imageInByte);
                        Game game1 = realm.where(Game.class).equalTo("id", gameIdGenerator).findFirst();
                        if(game1!=null) {
                            while (game1!=null) {
                                gameIdGenerator = rand.nextInt(99999) + 1;
                                if (game1==null) {
                                    newGameId.setId(gameIdGenerator);
                                    break;
                                }
                            }
                        }
                        else{
                            newGameId.setId(gameIdGenerator);
                        }

                        picture.setPicId();
                        realm.copyToRealm(newGameId);
                        realm.copyToRealm(picture);
                        finish();
                        Intent intent = new Intent(getBaseContext(), FirstRoundActivity.class);
                        intent.putExtra("Pic", picture.getImage());
                        startActivity(intent);

                    } else if (requestCode == 0) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        picture.setImage(byteArray);
                        Game game1 = realm.where(Game.class).equalTo("id", gameIdGenerator).findFirst();

                        if(game1!=null) {
                            while (game1!=null) {
                                gameIdGenerator = rand.nextInt(99999) + 1;
                                if (game1==null) {
                                    newGameId.setId(gameIdGenerator);
                                    break;
                                }
                            }
                        }
                        else{
                            newGameId.setId(gameIdGenerator);
                        }
                        picture.setPicId();

                        realm.copyToRealm(newGameId);
                        realm.copyToRealm(picture);
                        finish();
                        Intent intent = new Intent(getBaseContext(), FirstRoundActivity.class);
                        intent.putExtra("Pic", picture.getImage());
                        startActivity(intent);
                    }
                }
            });
        }
    }
}

