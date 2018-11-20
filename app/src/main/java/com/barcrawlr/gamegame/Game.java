package com.barcrawlr.gamegame;

import io.realm.RealmModel;
import io.realm.RealmObject;


public class Game extends RealmObject {

    private static int id;

    private String emptyConstructor;

    public String getEmptyConstructor() {
        return emptyConstructor;
    }

    public void setEmptyConstructor(String emptyConstructor) {
        this.emptyConstructor = emptyConstructor;
    }

    public static int getId() {
        return id;
    }

    public void setId(int id) {
        Game.id = id;
    }
}
