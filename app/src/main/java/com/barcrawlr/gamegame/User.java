package com.barcrawlr.gamegame;

import io.realm.RealmObject;

public class User extends RealmObject {

    private static int id;
    private int newId;

    public static int getNewId() {
        return getId();
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    public static int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
