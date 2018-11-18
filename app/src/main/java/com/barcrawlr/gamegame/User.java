package com.barcrawlr.gamegame;

import io.realm.RealmObject;

public class User extends RealmObject {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
