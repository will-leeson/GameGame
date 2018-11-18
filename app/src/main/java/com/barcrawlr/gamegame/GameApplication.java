package com.barcrawlr.gamegame;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class GameApplication extends Application {
    public static int pictureId = -1;
    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }

    public static int getPictureId() {
        return pictureId;
    }

    public static int setPictureId() {
        pictureId++;
        return pictureId;
    }
}
