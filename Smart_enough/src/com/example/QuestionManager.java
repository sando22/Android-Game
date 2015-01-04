package com.example;

import java.util.ArrayList;
import java.util.Random;

public class QuestionManager {
	private ArrayList<Question> questionList = new ArrayList<Question>();
	private FileManager fileManager = Menu.fileManager;
	private Random random = new Random();
	private String[] wholeFile;
	
	public void generateQuestionList(){
		Thread generateQuestionThread = new Thread(new Runnable() {
			@Override
			public void run() {
				int startingLine = 0;
				if (!questionList.isEmpty()){
					questionList.clear();
				}
				if (wholeFile == null){
					wholeFile = fileManager.readQuestionFile();
				}
				while (startingLine < wholeFile.length){
					questionList.add(createQuestion(startingLine));
					startingLine += 5;
				}
			}
		});
		generateQuestionThread.start();
		try {
			generateQuestionThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Question getNextQuestion(){
		int currentRandomPick;
		if (questionList.size() > 1){
			currentRandomPick = random.nextInt(questionList.size() - 1);
		} else {
			currentRandomPick = 0;
		}
		Question nextQuestion = questionList.get(currentRandomPick);
		questionList.remove(currentRandomPick);
		nextQuestion.shuffleAnswers();

		return nextQuestion;
	}
	
	private Question createQuestion(int startingLine){
		String question;
		ArrayList<String> answers = new ArrayList<String>();
		question = wholeFile[startingLine];
		for (int i = 1; i < 5; i++){
			answers.add(wholeFile[startingLine + i]);
		}
		return new Question(question, answers);
	}
	
	public boolean hasMoreQuestions(){
		return !questionList.isEmpty();
	}
	
}
