package com.barcrawlr.gamegame;

import java.io.Serializable;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Picture extends RealmObject implements Serializable {


    @PrimaryKey
    private static String id;
    private static  int picId;
    private byte[] image;
    private String word;
    private int score;

    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public static int getPicId() {
        return picId;
    }

    public static int setPicId() {
        picId++;
        return picId;
    }

    public static String getId() {
        return id;
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
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
