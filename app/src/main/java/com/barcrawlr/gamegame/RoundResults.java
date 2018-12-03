package com.barcrawlr.gamegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RoundResults extends AppCompatActivity {

    private RecyclerView roundrecycler;
    private RecyclerView.Adapter RoundAdapter;
    //RealmResults<Picture> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_results);
        RecyclerView roundrecycler = (RecyclerView) findViewById(R.id.round_recycler);

        ButterKnife.bind(this);

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Picture> results = realm.where(Picture.class)
                .findAllSorted("image",Sort.DESCENDING,"word",Sort.DESCENDING);

        RoundAdapter adapter = new RoundAdapter(results, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        roundrecycler.setLayoutManager(linearLayoutManager);

        roundrecycler.setAdapter(adapter);

    }
}
