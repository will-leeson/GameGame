package com.barcrawlr.gamegame;

import io.realm.RealmObject;

public class User extends RealmObject {

    private static int id;
    private int newId;

    private String fullName;
    private String userName;
    private String password;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
