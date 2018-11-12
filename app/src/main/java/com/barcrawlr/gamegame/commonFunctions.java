package com.barcrawlr.gamegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class commonFunctions {

    public static void printImage(byte[] picture, ImageView imageView){
        if (picture != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            imageView.setImageBitmap(bmp);
        }
    }

}
