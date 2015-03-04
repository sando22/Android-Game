package com.smart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuestionManager {
    private ArrayList<Question> questionList = new ArrayList<>();
    private FileManager fileManager = Menu.fileManager;
    private Random random = new Random();
    private String[] wholeFile;

    public void generateQuestionList() {
        Thread generateQuestionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int startingLine = 0;
                if (!questionList.isEmpty()) {
                    questionList.clear();
                }
                if (wholeFile == null) {
                    wholeFile = fileManager.readQuestionFile();
                }
                while (startingLine < wholeFile.length) {
                    questionList.add(createQuestion(startingLine));
                    startingLine += 6;
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

    public Question getNextQuestion(int diff) {
        int currentRandomPick;
        Question nextQuestion;
        do {
            currentRandomPick = random.nextInt(questionList.size() - 1);
            nextQuestion = questionList.get(currentRandomPick);
        } while (nextQuestion.getDifficulty() != diff);
        questionList.remove(currentRandomPick);
        nextQuestion.shuffleAnswers();

        return nextQuestion;
    }

    private Question createQuestion(int startingLine) {
        String question;
        ArrayList<String> answers = new ArrayList<>();
        int difficulty;
        question = wholeFile[startingLine];
        difficulty = Integer.parseInt(wholeFile[startingLine + 1]);
        answers.addAll(Arrays.asList(wholeFile).subList(startingLine + 2, startingLine + 6));
        return new Question(question, answers, difficulty);
    }

    public boolean hasMoreQuestions() {
        return !questionList.isEmpty();
    }

}
