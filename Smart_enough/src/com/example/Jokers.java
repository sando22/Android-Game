package com.example;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Jokers extends Activity {
	
	Toast help;
	int rightAnswerNumber;
	int i, j;
	static int[] used = new int[3];
	Random random = new Random();
	Button fifty,change,audience;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jokers);
		
		fifty = (Button) findViewById (R.id.fifty);
		change = (Button) findViewById (R.id.change);	
		audience = (Button) findViewById (R.id.audience);
		
		help = Toast.makeText(getBaseContext(), "bad", Toast.LENGTH_LONG);
		rightAnswerNumber = InGame.getCurrentRightAnswerNumber();
		do{
			i = random.nextInt(4);
			j = random.nextInt(4);
		} while (i==rightAnswerNumber || j==rightAnswerNumber || i==j);
		
		for(int k=0;k<3;k++){
			if (used[k] == 1){
				switch (k){
					case 0:
						audience.setBackgroundResource(R.drawable.joker_public_gg);
						audience.setEnabled(false);
						break;
					case 1:
						change.setBackgroundResource(R.drawable.joker_change_gg);
						change.setEnabled(false);
						break;
					case 2:
						fifty.setBackgroundResource(R.drawable.joker_50_gg);
						fifty.setEnabled(false);
						break;
				}
			}
		}
	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.audience:
			help.setText("Audience voted mostly for " + InGame.getCurrentRightAnswer());
			help.show();
			used[0] = 1;
			finish();
			break;
		case R.id.change:
			help.setText("Answer changed");
			help.show();
			InGame.resume_functionality = 0;
			used[1] = 1;
			finish();
			break;
		case R.id.fifty:
			fiftyButtonWork(i);
			fiftyButtonWork(j);
			used[2] = 1;
			finish();
			break;
		}
	}
	
	void fiftyButtonWork(int but){
		switch (but){
			case 0:
				InGame.N1.setEnabled(false);
				break;
			case 1:
				InGame.N2.setEnabled(false);
				break;
			case 2:
				InGame.N3.setEnabled(false);
				break;
			case 3:
				InGame.N4.setEnabled(false);
				break;
		}
	}
}