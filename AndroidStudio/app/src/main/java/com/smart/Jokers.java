package com.smart;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Jokers extends Activity {
	
	Toast helpToast;
	int rightAnswerNumber;
	int i, j;
	static int[] usedJokers = new int[3];
	Random random = new Random();
	Button fiftyButton, changeButton, audienceButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jokers);
		
		fiftyButton = (Button) findViewById (R.id.fiftyButton);
		changeButton = (Button) findViewById (R.id.changeButton);	
		audienceButton = (Button) findViewById (R.id.audienceButton);
		
		rightAnswerNumber = InGame.getCurrentRightAnswerNumber();
		do{
			i = random.nextInt(4);
			j = random.nextInt(4);
		} while (i==rightAnswerNumber || j==rightAnswerNumber || i==j);
		
		for(int k=0;k<3;k++){
			if (usedJokers[k] == 1){
				switch (k){
					case 0:
						audienceButton.setBackgroundResource(R.drawable.joker_public_gg);
						audienceButton.setEnabled(false);
						break;
					case 1:
						changeButton.setBackgroundResource(R.drawable.joker_change_gg);
						changeButton.setEnabled(false);
						break;
					case 2:
						fiftyButton.setBackgroundResource(R.drawable.joker_50_gg);
						fiftyButton.setEnabled(false);
						break;
				}
			}
		}
	}

	public void onClick(View v) {
		helpToast = Toast.makeText(getBaseContext(), "bad", Toast.LENGTH_LONG);
		switch(v.getId()) {
			case R.id.audienceButton:
				helpToast.setText("Audience voted mostly for " + InGame.getCurrentRightAnswer());
				helpToast.show();
				usedJokers[0] = 1;
				finish();
				break;
			case R.id.changeButton:
				helpToast.setText("Answer changed");
				helpToast.show();
				InGame.resumeFunctionality = 0;
				usedJokers[1] = 1;
				finish();
				break;
			case R.id.fiftyButton:
				fiftyButtonWork(i);
				fiftyButtonWork(j);
				usedJokers[2] = 1;
				finish();
				break;
		}
	}
	
	void fiftyButtonWork(int but){
		switch (but){
			case 0:
				InGame.answerButton1.setEnabled(false);
				break;
			case 1:
				InGame.answerButton2.setEnabled(false);
				break;
			case 2:
				InGame.answerButton3.setEnabled(false);
				break;
			case 3:
				InGame.answerButton4.setEnabled(false);
				break;
		}
	}
}