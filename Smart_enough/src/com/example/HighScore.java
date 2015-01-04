package com.example;

import java.util.ArrayList;
import java.util.Collections;

import com.example.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HighScore extends Activity {
	private TextView scoreText;
	private FileManager fileManager = Menu.fileManager;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscore);
		
		scoreText = (TextView) findViewById(R.id.highscoreScoreText);
		scoreText.setText(sortResult(fileManager.readScoreFile()));
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.highscoreDeleteButton:
				deleteFile(this.getString(R.string.filemanager_ScoresFile));
				finish();
				break;
			}
	}
	
	private String sortResult(ArrayList<String> lines){
		HighScoreComparator cmp = new HighScoreComparator();
		String stringToPost = "";
		
		Collections.sort(lines, cmp);
		if (lines.size() > 10){
			for (int i=lines.size()-1;i>9;i--){
				lines.remove(i);
			}
		}
		for (String string : lines) {
			stringToPost += string + "\n";
		}
		return stringToPost;
	}
}
