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
        RulesBody.setText(" - Correct Guess = 2 Point\n" +
                " - Best Photo = 1 Point\n" +
                " - Best Guess = 1 Point\n" +
                " - An entire round correct will earn round beginner 2 points");
    }
}
