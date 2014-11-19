package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import com.example.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class HighScore extends Activity {
	Button deleteButton;
	TextView scoreText;
	String output, score;
	ScrollView myScrollView;
	
	Runnable r = new Runnable() {
		
		@Override
		public void run() {
			scoreText.setText(readFile());
		}
	};
	
	Thread t = new Thread(r);
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscore);
		myScrollView = (ScrollView) findViewById(R.id.mainScroll);
		
		myScrollView.setVerticalScrollBarEnabled(false);
		myScrollView.setHorizontalScrollBarEnabled(false);
		
		deleteButton = (Button) findViewById(R.id.deleteButton);
		scoreText = (TextView) findViewById(R.id.scoreText);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.deleteButton:
				deleteFile("Scores.txt");
				finish();
				break;
			}
	}
	
	private String readFile(){
		ArrayList<String> als = new ArrayList<String>();
		try {
			InputStream is = openFileInput("Scores.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			while ((output=bufferedReader.readLine()) != null){
				als.add(output);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
		}
		return sortResult(als);
	}
	
	private String sortResult(ArrayList<String> lines){
		HighScoreComparator cmp = new HighScoreComparator();
		String toPost = "";
		
		Collections.sort(lines, cmp);
		if (lines.size() > 10){
			for (int i=lines.size()-1;i>14;i--){
				lines.remove(i);
			}
		}
		for (String string : lines) {
			toPost += string + "\n";
		}
		return toPost;
	}
}
