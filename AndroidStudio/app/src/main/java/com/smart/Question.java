package com.smart;

import java.util.ArrayList;
import java.util.Collections;

public class Question {
    private String questionTitle;
    private int correctAnswer;
    private ArrayList<String> answersList = new ArrayList<>();
    private int difficulty;

    public Question(String question, ArrayList<String> answers, int diff) {
        this.questionTitle = question;
        this.answersList = answers;
        this.difficulty = diff;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList<String> getAnswersList() {
        return answersList;
    }

    public void shuffleAnswers() {
        Thread shuffleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Collections.shuffle(answersList);
                for (int i = 0; i < 4; i++) {
                    if (answersList.get(i).contains("+")) {
                        correctAnswer = i;
                        answersList.set(i, answersList.get(i).substring(1));
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
