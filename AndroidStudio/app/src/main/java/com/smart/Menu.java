package com.smart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends Activity {
    public static FileManager fileManager;
    public static QuestionManager quesstionManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        fileManager = new FileManager(this);
        quesstionManager = new QuestionManager();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuStartButton:
                Intent myIntent = new Intent(Menu.this, InGame.class);
                Menu.this.startActivity(myIntent);
                break;
            case R.id.menuScoresButton:
                Intent myScoreIntent = new Intent(Menu.this, HighScoreSlider.class);
                Menu.this.startActivity(myScoreIntent);
                break;
        }
    }
}

