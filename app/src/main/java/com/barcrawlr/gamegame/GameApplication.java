package com.barcrawlr.gamegame;

import android.app.Application;

import io.realm.Realm;

public class GameApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        Realm.init(this);
    }
}
