package com.smart;

import java.util.ArrayList;
import java.util.Collections;

public class Question {
    private String questionTitle;
    private int correctAnswer;
    private ArrayList<String> answerList = new ArrayList<String>();
    private int difficulty;

    public Question(String question, ArrayList<String> answers, int diff) {
        this.questionTitle = question;
        this.answerList = answers;
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

    public ArrayList<String> getAnswerList() {
        return answerList;
    }

    public void shuffleAnswers() {
        Thread shuffleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Collections.shuffle(answerList);
                for (int i = 0; i < 4; i++) {
                    if (answerList.get(i).contains("+")) {
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
