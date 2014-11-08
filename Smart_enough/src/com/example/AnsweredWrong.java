package com.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AnsweredWrong extends Activity {
	Button save, menu;
	EditText name;
	TextView lastQuestion, rightAnswer;
	Toast saveToast;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answeredwrong);
		
		save = (Button) findViewById (R.id.saveresult);
		menu = (Button) findViewById (R.id.gotomenu);
		name = (EditText) findViewById(R.id.recordName);
		lastQuestion = (TextView) findViewById(R.id.lastquestion);
		rightAnswer = (TextView) findViewById(R.id.rightanswer);
		saveToast = Toast.makeText(getBaseContext(), "Result saved", Toast.LENGTH_SHORT);
		
		lastQuestion.setText(InGame.getCurrentQuestion());
		rightAnswer.setText("The right answer was: " + InGame.getCurrentRightAnswer());
		
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
		case R.id.saveresult:
			if (name.getText().toString().matches("")){
				saveToast.setText("Can't save without name!");
				saveToast.show();
			}else{
				writeToFile(String.valueOf(InGame.getRightAnswersCounter()) + " - " + name.getText().toString());
				saveToast.setText("Result saved");
				saveToast.show();
				finish();
			}
			break;
		case R.id.gotomenu:
			finish();
			break;
		}
	}
}
