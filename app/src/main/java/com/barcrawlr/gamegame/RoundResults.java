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

    private RecyclerView RoundRecycler;
    private RecyclerView.Adapter RoundAdapter;
    private ArrayList<Picture> Dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_results);

        ButterKnife.bind(this);

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Picture> results;
        String title;

        results = realm.where(Picture.class)
                .findAll()
                .sort("dateTimeStart", Sort.DESCENDING);

        RoundAdapter adapter = new RoundAdapter(results, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RoundRecycler.setLayoutManager(linearLayoutManager);

        RoundRecycler.setAdapter(adapter);


        /*RoundRecycler = (RecyclerView) findViewById(R.id.round_recycler);
        RoundRecycler.setLayoutManager(new LinearLayoutManager(this));

        RoundAdapter = new RoundAdapter(this, Dataset);
        RoundRecycler.setAdapter(RoundAdapter);*/
    }
}
