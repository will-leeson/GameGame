package com.barcrawlr.gamegame;

import android.service.autofill.FillEventHistory;

import io.realm.RealmModel;
import io.realm.RealmObject;


public class Game extends RealmObject {

    private int id;

    private String emptyConstructor;

    public String getEmptyConstructor() {
        return emptyConstructor;
    }

    public void setEmptyConstructor(String emptyConstructor) {
        this.emptyConstructor = emptyConstructor;
    }

    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
