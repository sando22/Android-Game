package com.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.example.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EndGame extends Activity {
	Button saveButton, menuButton;
	EditText recordName;
	TextView lastQuestion, rightAnswer;
	Toast saveToast;
	
	Runnable r = new Runnable() {
		
		@Override
		public void run() {
			writeToFile(String.valueOf(InGame.getRightAnswersCounter()) + " - " + recordName.getText().toString());
			
		}
	};
	
	Thread t = new Thread(r);
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.endgame);
		
		saveButton = (Button) findViewById (R.id.saveResult);
		menuButton = (Button) findViewById (R.id.goToMenu);
		recordName = (EditText) findViewById(R.id.recordName);
		lastQuestion = (TextView) findViewById(R.id.lastQuestion);
		rightAnswer = (TextView) findViewById(R.id.rightAnswer);
		saveToast = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);
		
		if (InGame.resumeFunctionality == 404){
			lastQuestion.setText(InGame.getCurrentQuestion());
			lastQuestion.setVisibility(View.VISIBLE);
			rightAnswer.setText("The right answer was: " + InGame.getCurrentRightAnswer());
			rightAnswer.setVisibility(View.VISIBLE);
		}
	}
	
	private void writeToFile(String data) {
	    try {
	    	FileOutputStream fOut = openFileOutput("Scores.txt", MODE_APPEND);
	        OutputStreamWriter osw = new OutputStreamWriter(fOut);
	        osw.write(data);
	        osw.append("\n");
	        osw.flush();
	        osw.close();
	    }
	    catch (IOException e) {
	    	e.printStackTrace();
	    } 
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.saveResult:
				if (InGame.resumeFunctionality == 100) InGame.thisActivity.finish();
				if (recordName.getText().toString().matches("")){
					saveToast.setText("Can't save without name!");
					saveToast.show();
				}else{
					t.start();
					try {
						t.join();
						saveToast.setText("Result saved");
						saveToast.show();
					} catch (InterruptedException e) {
						e.printStackTrace();
						saveToast.setText("Something fucked up!");
						saveToast.show();
					}
					finish();
				}
				break;
			case R.id.goToMenu:
				saveToast.setText("Result not saved");
				saveToast.show();
				InGame.thisActivity.finish();
				finish();
				break;
		}
	}
}
