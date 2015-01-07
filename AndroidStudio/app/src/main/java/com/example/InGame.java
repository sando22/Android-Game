package com.example;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InGame extends Activity {
	public static Activity ingameActivity;
	public static int resumeFunctionality = 0, rightAnswersCounter;
	public static Button answerButton1, answerButton2, answerButton3, answerButton4;
	public static Question question;
	
	private Handler handler = new Handler();
	private QuestionManager questionManager = Menu.quesstionManager;
	private TextView questionTextView, streakCounterTextView;
	private ArrayList<Button> answerButtonsList = new ArrayList<Button>();
	private Button jokerButton, homeButton;
	
	public static String getCurrentQuestion(){
		return question.getQuestionTitle();		
	}
	
	public static String getCurrentRightAnswer(){
		return question.getAnswerList().get(question.getCorrectAnswer());
	}
	
	public static int getRightAnswersCounter() {
		return rightAnswersCounter;
	}
	
	public static int getCurrentRightAnswerNumber(){
		return question.getCorrectAnswer();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingame);
		answerButton1 = (Button) findViewById (R.id.A);
		answerButton2 = (Button) findViewById (R.id.B);	
		answerButton3 = (Button) findViewById (R.id.C);
		answerButton4 = (Button) findViewById (R.id.D);
		answerButtonsList.add(answerButton1);
		answerButtonsList.add(answerButton2);
		answerButtonsList.add(answerButton3);
		answerButtonsList.add(answerButton4);
		questionTextView = (TextView) findViewById(R.id.ingameQuestion);
		streakCounterTextView = (TextView) findViewById(R.id.ingameStreakCounter);
		jokerButton = (Button) findViewById(R.id.ingameJokerButton);
		homeButton = (Button) findViewById(R.id.ingameMenuButton);
		streakCounterTextView.setText("Alpha testing!");
		rightAnswersCounter = 0;
		ingameActivity = this;
		questionManager.generateQuestionList();
	}
	
	@Override
	public void onBackPressed() {
		Intent myMenuIntent = new Intent(InGame.this, EndGame.class);
		InGame.this.startActivity(myMenuIntent);
		resumeFunctionality = 100;
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
		if (questionManager.hasMoreQuestions()){
			question = questionManager.getNextQuestion();
			setUiText();
		} else{
			Intent myEndgameIntent = new Intent(InGame.this, EndGame.class);
			InGame.this.startActivity(myEndgameIntent);
			resumeFunctionality = 100;
		}
	}
	
	private void setUiText(){
		int i = 0;
		questionTextView.setText(question.getQuestionTitle());
		ArrayList<String> answerList = question.getAnswerList();
		for (Button button : answerButtonsList) {
			button.setText(answerList.get(i));
			i++;
			button.setEnabled(true);
		}
	}
	
	public void onClick(View v) throws InterruptedException {
		switch(v.getId()) {
	        case R.id.A:
	        	if (question.getCorrectAnswer() == 0){
	        		answerButton1.setBackgroundResource(R.color.ingame_answered_right);
	        		setUnclickableButtons();
	        		handler.postDelayed(runRight, 500);
	        		rightAnswersCounter++;
	        		streakCounterTextView.setText("Streak of " + String.valueOf(rightAnswersCounter));
	        	}else{
	        		answerButton1.setBackgroundResource(R.color.ingame_answered_wrong);
	        		setUnclickableButtons();
	        		handler.postDelayed(runWrong, 500);
	        	}
	        	break;
	        case R.id.B:
	        	if (question.getCorrectAnswer() == 1){
	        		answerButton2.setBackgroundResource(R.color.ingame_answered_right);
	        		setUnclickableButtons();
	        		handler.postDelayed(runRight, 500);
	        		rightAnswersCounter++;
	        		streakCounterTextView.setText("Streak of " + String.valueOf(rightAnswersCounter));
	        	}else{
	        		answerButton2.setBackgroundResource(R.color.ingame_answered_wrong);
	        		setUnclickableButtons();
	        		handler.postDelayed(runWrong, 500);
	        	}
	        	break;
	        case R.id.C:
	        	if (question.getCorrectAnswer() == 2){
	        		answerButton3.setBackgroundResource(R.color.ingame_answered_right);
	        		setUnclickableButtons();
	        		handler.postDelayed(runRight, 500);
	        		rightAnswersCounter++;
	        		streakCounterTextView.setText("Streak of " + String.valueOf(rightAnswersCounter));
	        	}else{
	        		answerButton3.setBackgroundResource(R.color.ingame_answered_wrong);
	        		setUnclickableButtons();
	        		handler.postDelayed(runWrong, 500);
	        	}
	        	break;
	        case R.id.D:
	        	if (question.getCorrectAnswer() == 3){
	        		answerButton4.setBackgroundResource(R.color.ingame_answered_right);
	        		setUnclickableButtons();
	        		handler.postDelayed(runRight, 500);
	        		rightAnswersCounter++;
	        		streakCounterTextView.setText("Streak of " + String.valueOf(rightAnswersCounter));
	        	}else{
	        		answerButton4.setBackgroundResource(R.color.ingame_answered_wrong);
	        		setUnclickableButtons();
	        		handler.postDelayed(runWrong, 500);
	        	}
	        	break;
	        case R.id.ingameJokerButton:
	        	Intent myJokerIntent = new Intent(InGame.this, Jokers.class);
				InGame.this.startActivity(myJokerIntent);
	        	break;
	        case R.id.ingameMenuButton:
	        	Intent myEndgameIntent = new Intent(InGame.this, EndGame.class);
				InGame.this.startActivity(myEndgameIntent);
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
	
	private void setDefaultButton(){
		for (Button button : answerButtonsList) {
			button.setBackgroundResource(R.color.ingame_answer);
			button.setClickable(true);
		}
		jokerButton.setClickable(true);
		homeButton.setClickable(true);
	}
	
	private void setUnclickableButtons(){
		for (Button button : answerButtonsList) {
			button.setClickable(false);
		}
		jokerButton.setClickable(false);
		homeButton.setClickable(false);
	}
	
}
