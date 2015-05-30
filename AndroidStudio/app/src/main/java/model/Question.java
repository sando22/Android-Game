package model;

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
                for (String answer : answersList) {
                    if (answer.startsWith("+")) {
                        correctAnswer = answersList.indexOf(answer);
                        answersList.set(answersList.indexOf(answer), answer.substring(1));
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
