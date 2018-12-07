package com.barcrawlr.gamegame;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.Sort;

public class RoundResults extends AppCompatActivity {

    private FloatingActionButton floatButton;
    private RecyclerView roundrecycler;
    private RoundAdapter adapter;
    //Picture pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_results);
        roundrecycler = (RecyclerView) findViewById(R.id.round_recycler);
        floatButton = (FloatingActionButton) findViewById(R.id.floating_action_button);

        ButterKnife.bind(this);

        Realm realm = Realm.getDefaultInstance();

        final String id = (String) getIntent().getStringExtra("Pic");

        //pic = realm.where(Picture.class).equalTo("id", id).findFirst();

        final RealmResults<Picture> results = realm.where(Picture.class).findAll();

        adapter = new RoundAdapter(results, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        roundrecycler.setLayoutManager(linearLayoutManager);

        roundrecycler.setAdapter(adapter);

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LeaderboardActivity.class);
                intent.putExtra("Pic",id);
                startActivity(intent);
            }
        });

    }
}
