package com.barcrawlr.gamegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RulesPage extends AppCompatActivity {

    private TextView RulesTitle;
    private TextView RulesBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_page);

        RulesTitle = (TextView) findViewById(R.id.rules_title);
        RulesBody = (TextView) findViewById(R.id.rules_body);

        RulesTitle.setText("Rules:");
        RulesBody.setText("...");
    }
}
