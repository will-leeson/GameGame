package com.barcrawlr.gamegame;

import android.service.autofill.FillEventHistory;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Game extends RealmObject {

    @PrimaryKey
    private String id;

    private String emptyConstructor;

    public String getEmptyConstructor() {
        return emptyConstructor;
    }

//    public RealmList<Picture> pics;

    public void setEmptyConstructor(String emptyConstructor) {
        this.emptyConstructor = emptyConstructor;
    }

    public  String getId() {
        return id;
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }
}
