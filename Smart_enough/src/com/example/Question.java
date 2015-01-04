package com.example;

import java.util.ArrayList;
import java.util.Collections;

public class Question {
	private String questionTitle;
	private int correctAnswer;
	private ArrayList<String> answerList = new ArrayList<String>();
	
	public Question(String question, ArrayList<String> answers) {
		this.questionTitle = question;
		this.answerList = answers;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}
	
	public int getCorrectAnswer(){
		return correctAnswer;
	}

	public ArrayList<String> getAnswerList() {
		return answerList;
	}
	
	public void shuffleAnswers(){
		Thread shuffleThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Collections.shuffle(answerList);
				for (int i = 0; i < 4; i++) {
					if (answerList.get(i).contains("+")){
						correctAnswer = i;
						answerList.set(i, answerList.get(i).substring(1));
					}
				}
			}
		});
		shuffleThread.start();
		try {
			shuffleThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
