package com.barcrawlr.gamegame;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Picture extends RealmObject {


    //@PrimaryKey
    private static int picId =-1;
    private static int id;
    private byte[] image;
    private String word;
    private int score;

    public static int getPicId() {
        return picId;
    }

    public static int setPicId() {
        picId++;
        return picId;
    }

    public static int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}