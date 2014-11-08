package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu   extends Activity {
	
	Button F1,F2,F3;
	Toast toast;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		F1=	(Button) findViewById(R.id.Start);
		F2 = (Button) findViewById(R.id.Scores);	
		F3 = (Button) findViewById(R.id.Exit);
		toast = Toast.makeText(getBaseContext(), "Press exit to leave", Toast.LENGTH_SHORT);
		
	}
	
	@Override
	public void onBackPressed() {
		toast.show();
		return;
	}
	
	public void onClick(View v) {
		toast.cancel();
		switch(v.getId()) {
	        case R.id.Start:
	        	Intent myIntent = new Intent(Menu.this, InGame.class);
				Menu.this.startActivity(myIntent);
				break;
	        case R.id.Scores:
				Intent myScoreIntent = new Intent(Menu.this, HighScore.class);
				Menu.this.startActivity(myScoreIntent);
				break;
	        case R.id.Exit:
	        	finish();
		}
	}
}

