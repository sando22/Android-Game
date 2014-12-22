package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.example.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InGame extends Activity {
	
	public static Activity thisActivity;
	static int correctAnswer, resumeFunctionality = 0;
	int linesCount=0;
	static int rightAnswersCounter;
	ArrayList<Integer> invalidNumbers = new ArrayList<Integer>();
	static Button answerButton1;
	static Button answerButton2;
	static Button answerButton3;
	static Button answerButton4;
	TextView question, streakCounter;
	static String wholeFile[];
	AssetManager am;
	static int questionIndex;
	boolean correct = true;
	static String[] answersToPreview = new String[4];
	Random random = new Random();
	Handler handler = new Handler(); 
	
	Runnable runRight = new Runnable() {
		 public void run() { 
             init();
			 setDefaultButton();
        } 
	};
	
	Runnable runWrong = new Runnable() {
		 public void run() { 
			 goToAnsweredWrong();
			 setDefaultButton();
       } 
	};
	
	Runnable readFile = new Runnable() {
		public void run() {
			int i = 0;
			String line;
			try {
				am = getAssets();
				InputStream is = am.open("file.txt");
				InputStreamReader inputStreamReader = new InputStreamReader(is);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				while ((line=bufferedReader.readLine()) != null && i < linesCount){
					wholeFile[i] = line;
					i++;
				}
				bufferedReader.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingame);
		answerButton1 = (Button) findViewById (R.id.A);
		answerButton2 = (Button) findViewById (R.id.B);	
		answerButton3 = (Button) findViewById (R.id.C);
		answerButton4 = (Button) findViewById (R.id.D);
		question = (TextView) findViewById(R.id.Question);
		streakCounter = (TextView) findViewById(R.id.StreakCounter);
		streakCounter.setText("Show your knowledge");
		rightAnswersCounter = 0;
		try {
			countLines();
		} catch (IOException e) {
			e.printStackTrace();
		}
		wholeFile = new String[linesCount];		
		readFile.run();
		thisActivity = this;
	}
	
	protected void onResume() {
		super.onResume();
		if (resumeFunctionality == 0){
			init();
			resumeFunctionality++;
		}
	}
	
	protected void onDestroy(){
		super.onDestroy();
		resumeFunctionality = 0;
		for (int i=0;i<3;i++) Jokers.usedJokers[i] = 0;
	}
	
	protected void init(){
		correctAnswer = 5;
		
		getNextQuestion();
		
		shuffleArray(answersToPreview);
		
		setButtonText();
	}
	
	private void getNextQuestion(){
		while (true){
			questionIndex = random.nextInt(linesCount/5) *5;
			if (invalidNumbers.contains(questionIndex)){
				if (invalidNumbers.size() >= linesCount/5){
					Intent myEndGameIntent = new Intent(InGame.this, EndGame.class);
					InGame.this.startActivity(myEndGameIntent);
					finish();
					break;
				}else continue;
			}else{
				question.setText(wholeFile[questionIndex]);
				invalidNumbers.add(questionIndex);
				for(int i=0;i<4;i++){
					answersToPreview[i] = wholeFile[questionIndex + i + 1];
				}
				break;
			}
		}	
	}
	
	private void shuffleArray(String[] answers2){
	    for (int i = answers2.length - 1; i > 0; i--){
	      int index = random.nextInt(i + 1);
	      String a = answers2[index];
	      answers2[index] = answers2[i];
	      answers2[i] = a;
	    }
	 }

	private void setButtonText(){
		for(int i=0;i<4;i++){
			if (answersToPreview[i].contains("+")){
				answersToPreview[i] = answersToPreview[i].substring(1);
				correctAnswer = i;
			}
			switch (i){
				case 0:
					answerButton1.setText(answersToPreview[i]);
				case 1:
					answerButton2.setText(answersToPreview[i]);
				case 2:
					answerButton3.setText(answersToPreview[i]);
				case 3:
					answerButton4.setText(answersToPreview[i]);
			}
			answerButton1.setEnabled(true);
			answerButton2.setEnabled(true);
			answerButton3.setEnabled(true);
			answerButton4.setEnabled(true);
		}
	}
	
	private void countLines() throws IOException {
		am = getAssets();
		InputStream is = am.open("file.txt");
		InputStreamReader inputStreamReader = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		linesCount = 0;
		while (reader.readLine() != null) linesCount++;
		reader.close();
	}
	
	@Override
	public void onBackPressed() {
		Intent myMenuIntent = new Intent(InGame.this, EndGame.class);
		InGame.this.startActivity(myMenuIntent);
		resumeFunctionality = 100;
	}
	
	public void onClick(View v) throws InterruptedException {
		switch(v.getId()) {
	        case R.id.A:
	        	if (correctAnswer == 0){
	        		answerButton1.setBackgroundResource(R.color.ingame_answered_right);
	        		setUnclickableButtons();
	        		handler.postDelayed(runRight, 500);
	        		rightAnswersCounter++;
	        		streakCounter.setText("Streak of " + String.valueOf(rightAnswersCounter));
	        	}else{
	        		answerButton1.setBackgroundResource(R.color.ingame_answered_wrong);
	        		setUnclickableButtons();
	        		handler.postDelayed(runWrong, 500);
	        	}
	        	break;
	        case R.id.B:
	        	if (correctAnswer == 1){
	        		answerButton2.setBackgroundResource(R.color.ingame_answered_right);
	        		setUnclickableButtons();
	        		handler.postDelayed(runRight, 500);
	        		rightAnswersCounter++;
	        		streakCounter.setText("Streak of " + String.valueOf(rightAnswersCounter));
	        	}else{
	        		answerButton2.setBackgroundResource(R.color.ingame_answered_wrong);
	        		setUnclickableButtons();
	        		handler.postDelayed(runWrong, 500);
	        	}
	        	break;
	        case R.id.C:
	        	if (correctAnswer == 2){
	        		answerButton3.setBackgroundResource(R.color.ingame_answered_right);
	        		setUnclickableButtons();
	        		handler.postDelayed(runRight, 500);
	        		rightAnswersCounter++;
	        		streakCounter.setText("Streak of " + String.valueOf(rightAnswersCounter));
	        	}else{
	        		answerButton3.setBackgroundResource(R.color.ingame_answered_wrong);
	        		setUnclickableButtons();
	        		handler.postDelayed(runWrong, 500);
	        	}
	        	break;
	        case R.id.D:
	        	if (correctAnswer == 3){
	        		answerButton4.setBackgroundResource(R.color.ingame_answered_right);
	        		setUnclickableButtons();
	        		handler.postDelayed(runRight, 500);
	        		rightAnswersCounter++;
	        		streakCounter.setText("Streak of " + String.valueOf(rightAnswersCounter));
	        	}else{
	        		answerButton4.setBackgroundResource(R.color.ingame_answered_wrong);
	        		setUnclickableButtons();
	        		handler.postDelayed(runWrong, 500);
	        	}
	        	break;
	        case R.id.Jokers:
	        	Intent myJokerIntent = new Intent(InGame.this, Jokers.class);
				InGame.this.startActivity(myJokerIntent);
	        	break;
	        case R.id.Menu:
	        	Intent myMenuIntent = new Intent(InGame.this, EndGame.class);
				InGame.this.startActivity(myMenuIntent);
				resumeFunctionality = 100;
				break;
		}
	}
	
	void goToAnsweredWrong(){
		Intent myAnsweredWrongIntent = new Intent(InGame.this, EndGame.class);
		InGame.this.startActivity(myAnsweredWrongIntent);
		resumeFunctionality = 404;
		finish();
	}

	public static int getRightAnswersCounter() {
		return rightAnswersCounter;
	}
	
	public static String getCurrentQuestion(){
		return wholeFile[questionIndex];		
	}
	
	public static String getCurrentRightAnswer(){
		return answersToPreview[correctAnswer];
	}
	
	public static int getCurrentRightAnswerNumber(){
		return correctAnswer;
	}
	
	private void setDefaultButton(){
		answerButton1.setBackgroundResource(R.color.ingame);
		answerButton2.setBackgroundResource(R.color.ingame);
		answerButton3.setBackgroundResource(R.color.ingame);
		answerButton4.setBackgroundResource(R.color.ingame);
		answerButton1.setClickable(true);
		answerButton2.setClickable(true);
		answerButton3.setClickable(true);
		answerButton4.setClickable(true);
	}
	
	private void setUnclickableButtons(){
		answerButton1.setClickable(false);
		answerButton2.setClickable(false);
		answerButton3.setClickable(false);
		answerButton4.setClickable(false);
	}
	
}
