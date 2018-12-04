package com.barcrawlr.gamegame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

public class PictureActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private Realm realm;
    TextView textTargetUri;
    private Button buttonLoadImage;
    private Bitmap bitmap;
    private Random rand = new Random();
    private String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    private int gameIdGenerator;
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
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File

                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                                "com.example.android.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
            }
        });
    }
    protected File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        PictureActivity.super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO || requestCode == 0 && resultCode == RESULT_OK) {

            gameIdGenerator = rand.nextInt(99999) + 1;
            if (requestCode == 1) {

                //Bundle extras = data.getExtras();
                //Bitmap imageBitmap = (Bitmap) extras.get("data");

                // imageButton.setImageBitmap(imageBitmap);
                Glide.with(this).load(mCurrentPhotoPath).into(imageButton);


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
                        Game game1 = realm.where(Game.class).equalTo("id", gameIdGenerator).findFirst();
                        //  newGame.contains(newGame.sort("id").where().equalTo("id", gameIdGenerator).);
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

                        //user.setId(242341);
                        //  realm.copyFromRealm(user);
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
                        //bitmap.recycle();
                        picture.setImage(byteArray);
                        Game game1 = realm.where(Game.class).equalTo("id", gameIdGenerator).findFirst();
                        //  newGame.contains(newGame.sort("id").where().equalTo("id", gameIdGenerator).);
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

                    }
                }
            });
        }
    }
}

