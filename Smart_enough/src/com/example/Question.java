package com.example;

import java.util.ArrayList;

public class Question {
	private String questionTitle;
	private int correctAnswer;
	private ArrayList<String> answerList = new ArrayList<String>();
	
	public Question(String q, int c, ArrayList<String> a) {
		this.questionTitle = q;
		this.correctAnswer = c;
		this.answerList = a;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public ArrayList<String> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(ArrayList<String> answerList) {
		this.answerList = answerList;
	}
	
}
