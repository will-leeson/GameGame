package com.barcrawlr.gamegame;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Picture extends RealmObject {
    // @PrimaryKey
    // private String id;
    private byte[] image;

    /**  public String getId() {
     return id;
     }

     public void setId(String id) {
     this.id = id;
     }
     **/
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
