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
	int i=0;
	static int correct_answer, resume_functionality = 0;
	int linescount=0;
	static int rightAnswersCounter;
	ArrayList<Integer> invalidNumbers = new ArrayList<Integer>();
	static Button N1;
	static Button N2;
	static Button N3;
	static Button N4;
	TextView Question, StreakCounter;
	static String wholeFile[];
	String line = null;
	AssetManager am;
	static int questionIndex;
	boolean correct = true;
	static String[] answers = new String[4];
	Random random = new Random();
	Handler handler = new Handler(); 
	
	Runnable run_right = new Runnable() {
		 public void run() { 
             init();
			 setDefaultButton();
        } 
	};
	
	Runnable run_wrong = new Runnable() {
		 public void run() { 
			 goToAnsweredWrong();
			 setDefaultButton();
       } 
	};
	
	Runnable read_file = new Runnable() {
		public void run() {
			i = 0;
			try {
				am = getAssets();
				InputStream is = am.open("file.txt");
				InputStreamReader inputStreamReader = new InputStreamReader(is);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				while ((line=bufferedReader.readLine()) != null && i < linescount){
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
		N1 = (Button) findViewById (R.id.A);
		N2 = (Button) findViewById (R.id.B);	
		N3 = (Button) findViewById (R.id.C);
		N4 = (Button) findViewById (R.id.D);
		Question = (TextView) findViewById(R.id.Question);
		StreakCounter = (TextView) findViewById(R.id.StreakCounter);
		StreakCounter.setText("Show your knowledge");
		rightAnswersCounter = 0;
		try {
			countLines();
		} catch (IOException e) {
			e.printStackTrace();
		}
		wholeFile = new String[linescount];		
		read_file.run();
		thisActivity = this;
	}
	
	protected void onResume() {
		super.onResume();
		if (resume_functionality == 0){
			init();
			resume_functionality++;
		}
	}
	
	protected void onDestroy(){
		super.onDestroy();
		resume_functionality = 0;
		for (int i=0;i<3;i++) Jokers.used[i] = 0;
	}
	
	protected void init(){
		i = 0;
		correct_answer = 5;
		
		getNextQuestion();
		
		shuffleArray(answers);
		
		setButtonText();
	}
	
	private void getNextQuestion(){
		while (true){
			questionIndex = random.nextInt(linescount/5) *5;
			if (invalidNumbers.contains(questionIndex)){
				if (invalidNumbers.size() >= linescount/5){
					Intent myEndGameIntent = new Intent(InGame.this, EndGame.class);
					InGame.this.startActivity(myEndGameIntent);
					finish();
					break;
				}else continue;
			}else{
				Question.setText(wholeFile[questionIndex]);
				invalidNumbers.add(questionIndex);
				for(i=0;i<4;i++){
					answers[i] = wholeFile[questionIndex + i + 1];
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
		for(i=0;i<4;i++){
			if (answers[i].contains("+")){
				answers[i] = answers[i].substring(1);
				correct_answer = i;
			}
			switch (i){
				case 0:
					N1.setText(answers[i]);
				case 1:
					N2.setText(answers[i]);
				case 2:
					N3.setText(answers[i]);
				case 3:
					N4.setText(answers[i]);
			}
			N1.setEnabled(true);
			N2.setEnabled(true);
			N3.setEnabled(true);
			N4.setEnabled(true);
		}
	}
	
	private void countLines() throws IOException {
		am = getAssets();
		InputStream is = am.open("file.txt");
		InputStreamReader inputStreamReader = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		linescount = 0;
		while (reader.readLine() != null) linescount++;
		reader.close();
	}
	
	@Override
	public void onBackPressed() {
		Intent myMenuIntent = new Intent(InGame.this, MenuEndGame.class);
		InGame.this.startActivity(myMenuIntent);
		return;
	}
	
	public void onClick(View v) throws InterruptedException {
		switch(v.getId()) {
	        case R.id.A:
	        	if (correct_answer == 0){
	        		N1.setBackgroundResource(R.color.ingame_answered_right);
	        		setUnclickableButtons();
	        		handler.postDelayed(run_right, 500);
	        		rightAnswersCounter++;
	        		StreakCounter.setText("Streak of " + String.valueOf(rightAnswersCounter));
	        	}else{
	        		N1.setBackgroundResource(R.color.ingame_answered_wrong);
	        		setUnclickableButtons();
	        		handler.postDelayed(run_wrong, 500);
	        	}
	        	break;
	        case R.id.B:
	        	if (correct_answer == 1){
	        		N2.setBackgroundResource(R.color.ingame_answered_right);
	        		setUnclickableButtons();
	        		handler.postDelayed(run_right, 500);
	        		rightAnswersCounter++;
	        		StreakCounter.setText("Streak of " + String.valueOf(rightAnswersCounter));
	        	}else{
	        		N2.setBackgroundResource(R.color.ingame_answered_wrong);
	        		setUnclickableButtons();
	        		handler.postDelayed(run_wrong, 500);
	        	}
	        	break;
	        case R.id.C:
	        	if (correct_answer == 2){
	        		N3.setBackgroundResource(R.color.ingame_answered_right);
	        		setUnclickableButtons();
	        		handler.postDelayed(run_right, 500);
	        		rightAnswersCounter++;
	        		StreakCounter.setText("Streak of " + String.valueOf(rightAnswersCounter));
	        	}else{
	        		N3.setBackgroundResource(R.color.ingame_answered_wrong);
	        		setUnclickableButtons();
	        		handler.postDelayed(run_wrong, 500);
	        	}
	        	break;
	        case R.id.D:
	        	if (correct_answer == 3){
	        		N4.setBackgroundResource(R.color.ingame_answered_right);
	        		setUnclickableButtons();
	        		handler.postDelayed(run_right, 500);
	        		rightAnswersCounter++;
	        		StreakCounter.setText("Streak of " + String.valueOf(rightAnswersCounter));
	        	}else{
	        		N4.setBackgroundResource(R.color.ingame_answered_wrong);
	        		setUnclickableButtons();
	        		handler.postDelayed(run_wrong, 500);
	        	}
	        	break;
	        case R.id.Jokers:
	        	Intent myJokerIntent = new Intent(InGame.this, Jokers.class);
				InGame.this.startActivity(myJokerIntent);
	        	break;
	        case R.id.Menu:
	        	Intent myMenuIntent = new Intent(InGame.this, MenuEndGame.class);
				InGame.this.startActivity(myMenuIntent);
				break;
		}
	}
	
	void goToAnsweredWrong(){
		Intent myAnsweredWrongIntent = new Intent(InGame.this, AnsweredWrong.class);
		InGame.this.startActivity(myAnsweredWrongIntent);
		finish();
	}

	public static int getRightAnswersCounter() {
		return rightAnswersCounter;
	}
	
	public static String getCurrentQuestion(){
		return wholeFile[questionIndex];		
	}
	
	public static String getCurrentRightAnswer(){
		return answers[correct_answer];
	}
	
	public static int getCurrentRightAnswerNumber(){
		return correct_answer;
	}
	
	void setDefaultButton(){
		N1.setBackgroundResource(R.color.ingame);
		N2.setBackgroundResource(R.color.ingame);
		N3.setBackgroundResource(R.color.ingame);
		N4.setBackgroundResource(R.color.ingame);
		N1.setClickable(true);
		N2.setClickable(true);
		N3.setClickable(true);
		N4.setClickable(true);
	}
	
	void setUnclickableButtons(){
		N1.setClickable(false);
		N2.setClickable(false);
		N3.setClickable(false);
		N4.setClickable(false);
	}

}
