package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class HighScore extends Activity {
	Button Back;
	TextView Scoretext;
	String Output, Score;
	ScrollView myScrollView;
	int i;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscore);
		myScrollView = (ScrollView) findViewById(R.id.mainScroll);
		
		myScrollView.setVerticalScrollBarEnabled(false);
		myScrollView.setHorizontalScrollBarEnabled(false);
		
		Back = (Button) findViewById (R.id.backbutton);
		Scoretext = (TextView) findViewById(R.id.scoretext);
		
		Scoretext.setText(readFile());
		
	}
	
	private String readFile(){
		String lines = "";
		try {
			InputStream is = openFileInput("Scores.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(is);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			while ((Output=bufferedReader.readLine()) != null){
				lines += Output + " \n ";
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
		}
		return lines;
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.backbutton:
			finish();
			break;
		case R.id.deletebutton:
			deleteFile("Scores.txt");
			finish();
			break;
		}
	}
}
