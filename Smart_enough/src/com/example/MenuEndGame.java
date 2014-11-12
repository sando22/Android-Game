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
import android.widget.Toast;

public class MenuEndGame extends Activity {
	Button save, menu, back;
	EditText name;
	Toast saveToast;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuendgame);
		
		back = (Button) findViewById(R.id.backtogame);
		save = (Button) findViewById (R.id.saveresult);
		menu = (Button) findViewById (R.id.gotomenu);
		name = (EditText) findViewById(R.id.recordName);
		saveToast = Toast.makeText(getBaseContext(), "Result saved", Toast.LENGTH_SHORT);
		
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
				InGame.thisActivity.finish();
				finish();
			}
			break;
		case R.id.gotomenu:
			InGame.thisActivity.finish();
			finish();
			break;
		case R.id.backtogame:
			finish();
			break;
		}
	}
}
