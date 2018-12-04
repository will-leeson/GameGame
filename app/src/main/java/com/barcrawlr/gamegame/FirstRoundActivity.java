package com.barcrawlr.gamegame;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.OutputStream;

public class FirstRoundActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    EditText answer;
    ImageView choosenImage;
    Button saveImage;

    Bitmap bmp;
    Bitmap alteredBitmap;
    Canvas canvas;
    Paint paint;
    Matrix matrix;
    float downx = 0;
    float downy = 0;
    float upx = 0;
    float upy = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_round);

        answer = (EditText) findViewById(R.id.answerText);
        choosenImage = (ImageView) findViewById(R.id.choosenImage);
        saveImage = (Button) findViewById(R.id.saveImage1);
        final byte[] picture = getIntent().getExtras().getByteArray("Pic");

        saveImage.setOnClickListener(this);
        choosenImage.setOnClickListener(this);
        choosenImage.setOnTouchListener(this);

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inScaled = true;
        opt.inMutable = true;
        Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length, opt);

        choosenImage.setImageBitmap(bmp);
        canvas = new Canvas(bmp);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
        matrix = new Matrix();
    }

    public void onClick(View v){
        if(v == saveImage){
            if(alteredBitmap!= null) {
                ContentValues contentValues = new ContentValues(3);
                contentValues.put(MediaStore.Audio.Media.DISPLAY_NAME, "Draw on me");

                Uri imageFileUri = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues);
                try {
                    OutputStream imageFileOS = getContentResolver().openOutputStream(imageFileUri);
                    alteredBitmap.compress(Bitmap.CompressFormat.JPEG, 90, imageFileOS);
                    Toast t = Toast.makeText(this, "Saved", Toast.LENGTH_LONG);
                    t.show();
                } catch (Exception e) {
                    Log.v("Exception", e.getMessage());
                }
                Intent intent =  new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                upx = event.getX();
                upy = event.getY();
                canvas.drawLine(downx, downy, upx, upy, paint);
                choosenImage.invalidate();
                downx = upx;
                downy = upy;
                break;
            case MotionEvent.ACTION_UP:
                upx = event.getX();
                upy = event.getY();
                canvas.drawLine(downx, downy, upx, upy, paint);
                choosenImage.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }
}
