package com.barcrawlr.gamegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class PlayActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        imageView = (ImageView)findViewById(R.id.targetimage);

        byte[] picture= getIntent().getExtras().getByteArray("Pic");


        //view = inflater.inflate(R.layout.fragment_fragment_retrieve, container, false);
        if( picture!= null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            imageView.setImageBitmap(bmp);
        }
    }
}
