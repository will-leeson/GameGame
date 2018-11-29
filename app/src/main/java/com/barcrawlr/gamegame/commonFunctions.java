package com.barcrawlr.gamegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.widget.ImageView;

public class commonFunctions {

    static Bitmap bmp;
    static Canvas canvas;
    public static Canvas printImage(byte[] picture, ImageView imageView, Boolean draw){
        if (picture != null) {
            bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            imageView.setImageBitmap(bmp);
            if (draw==true){
                canvas = new Canvas(bmp);
            }

        }

       if(draw==true){
           return canvas;
       }
       else{
            return null;
       }
    }

}
