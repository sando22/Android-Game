package com.example;

import com.example.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu   extends Activity {
	
	Button startButton,scoresButton;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		startButton=	(Button) findViewById(R.id.menuStartButton);
		scoresButton = (Button) findViewById(R.id.menuScoresButton);	
		
	}
	
	@Override
	public void onBackPressed() {
		finish();
		return;
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
	        case R.id.menuStartButton:
	        	Intent myIntent = new Intent(Menu.this, InGame.class);
				Menu.this.startActivity(myIntent);
				break;
	        case R.id.menuScoresButton:
				Intent myScoreIntent = new Intent(Menu.this, HighScore.class);
				Menu.this.startActivity(myScoreIntent);
				break;
		}
	}
}

