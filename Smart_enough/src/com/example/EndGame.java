package com.example;

import com.example.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EndGame extends Activity {
	private EditText recordName;
	private TextView lastQuestion;
	private Toast saveToast;
	private FileManager fileManager = Menu.fileManager;
	ProgressDialog dialog;
	Handler handler;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.endgame);
		
		recordName = (EditText) findViewById(R.id.endgameRecordName);
		lastQuestion = (TextView) findViewById(R.id.lastQuestion);
		
		if (InGame.resumeFunctionality == 404){
			lastQuestion.setText(InGame.getCurrentQuestion());
			lastQuestion.setVisibility(View.VISIBLE);
		}
	}
	
	public void onClick(View v) {
		saveToast = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);
		switch(v.getId()) {
			case R.id.endgameSaveButton:
				if (recordName.getText().toString().matches("")){
					saveToast.setText("Can't save without name!");
					saveToast.show();
				}else{
					fileManager.writeScoreFile(String.valueOf(InGame.getRightAnswersCounter()) + " - " + recordName.getText().toString());
					saveToast.setText("saved");
					saveToast.show();
					finish();
				}
				if (InGame.resumeFunctionality == 100) InGame.ingameActivity.finish();
				break;
			case R.id.endgameMenuButton:
				InGame.ingameActivity.finish();
				finish();
				break;
		}
	}
}
