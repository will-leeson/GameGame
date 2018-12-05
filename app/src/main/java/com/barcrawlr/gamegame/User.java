package com.barcrawlr.gamegame;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private static String id;
    private int newId;

    public static String getNewId() {
        return getId();
    }

    public void setNewId() {
        this.newId = newId;
    }

    public static String getId() {
        return id;
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }
}
