package com.smart;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class InGame extends Activity implements JokersFragment.FragmentCommunicator {
    public static Activity ingameActivity;
    public static int resumeFunctionality = 0, answeredRightCounter = 0;
    public static Button answerButton1, answerButton2, answerButton3, answerButton4;
    public static Question question;
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
    private Handler handler = new Handler();
    private QuestionManager questionManager = Menu.quesstionManager;
    private TextView questionTextView, streakCounterTextView;
    private ArrayList<Button> answerButtonsList = new ArrayList<>();
    private ImageButton jokerButton;
    private ProgressBar resultProgressBar;
    private FragmentManager fragmentManager;

    public static String getCurrentQuestion() {
        return question.getQuestionTitle();
    }

    public static String getCurrentRightAnswer() {
        return question.getAnswerList().get(question.getCorrectAnswer());
    }

    public static int getAnsweredRightCounter() {
        return answeredRightCounter;
    }

    public static int getCurrentRightAnswerNumber() {
        return question.getCorrectAnswer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame);
        answerButton1 = (Button) findViewById(R.id.A);
        answerButton2 = (Button) findViewById(R.id.B);
        answerButton3 = (Button) findViewById(R.id.C);
        answerButton4 = (Button) findViewById(R.id.D);
        answerButtonsList.add(answerButton1);
        answerButtonsList.add(answerButton2);
        answerButtonsList.add(answerButton3);
        answerButtonsList.add(answerButton4);
        questionTextView = (TextView) findViewById(R.id.ingameQuestion);
        streakCounterTextView = (TextView) findViewById(R.id.ingameStreakCounter);
        jokerButton = (ImageButton) findViewById(R.id.ingameJokerButton);
        resultProgressBar = (ProgressBar) findViewById(R.id.resultProgressBar);
        streakCounterTextView.setText("Beta testing!");
        ingameActivity = this;
        fragmentManager = getFragmentManager();
        questionManager.generateQuestionList();
    }

    @Override
    public void onBackPressed() {
        Intent myEndgameIntent = new Intent(InGame.this, EndGame.class);
        InGame.this.startActivity(myEndgameIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (resumeFunctionality == 0) {
            init();
            resumeFunctionality++;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resumeFunctionality = 0;
        answeredRightCounter = 0;
        for (int i = 0; i < 3; i++) JokersFragment.usedJokers[i] = 0;
    }

    protected void init() {
        if (questionManager.hasMoreQuestions() && answeredRightCounter < 30) {
            question = questionManager.getNextQuestion(answeredRightCounter / 10);
            setUiText();
        } else {
            Intent myEndgameIntent = new Intent(InGame.this, EndGame.class);
            InGame.this.startActivity(myEndgameIntent);
            finish();
        }
    }

    private void setUiText() {
        int i = 0;
        //streakCounterTextView.setText(String.valueOf(answeredRightCounter));
        questionTextView.setText(question.getQuestionTitle());
        ArrayList<String> answerList = question.getAnswerList();
        for (Button button : answerButtonsList) {
            button.setText(answerList.get(i));
            i++;
            resultProgressBar.setProgress(answeredRightCounter);
            button.setEnabled(true);
        }
    }

    public void onClick(View v) throws InterruptedException {
        switch (v.getId()) {
            case R.id.A:
                if (question.getCorrectAnswer() == 0) {
                    answerButton1.setBackgroundResource(R.drawable.ingame_answered_right);
                    setUnclickableButtons();
                    handler.postDelayed(runRight, 750);
                    answeredRightCounter++;
                } else {
                    answerButton1.setBackgroundResource(R.drawable.ingame_answered_wrong);
                    setUnclickableButtons();
                    handler.postDelayed(runWrong, 750);
                }
                break;
            case R.id.B:
                if (question.getCorrectAnswer() == 1) {
                    answerButton2.setBackgroundResource(R.drawable.ingame_answered_right);
                    setUnclickableButtons();
                    handler.postDelayed(runRight, 750);
                    answeredRightCounter++;
                } else {
                    answerButton2.setBackgroundResource(R.drawable.ingame_answered_wrong);
                    setUnclickableButtons();
                    handler.postDelayed(runWrong, 750);
                }
                break;
            case R.id.C:
                if (question.getCorrectAnswer() == 2) {
                    answerButton3.setBackgroundResource(R.drawable.ingame_answered_right);
                    setUnclickableButtons();
                    handler.postDelayed(runRight, 750);
                    answeredRightCounter++;
                } else {
                    answerButton3.setBackgroundResource(R.drawable.ingame_answered_wrong);
                    setUnclickableButtons();
                    handler.postDelayed(runWrong, 750);
                }
                break;
            case R.id.D:
                if (question.getCorrectAnswer() == 3) {
                    answerButton4.setBackgroundResource(R.drawable.ingame_answered_right);
                    setUnclickableButtons();
                    handler.postDelayed(runRight, 750);
                    answeredRightCounter++;
                } else {
                    answerButton4.setBackgroundResource(R.drawable.ingame_answered_wrong);
                    setUnclickableButtons();
                    handler.postDelayed(runWrong, 750);
                }
                break;
            case R.id.ingameJokerButton:
                JokersFragment jokerFragment = (JokersFragment) fragmentManager.findFragmentByTag("JOKERFRAGMENT");
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (jokerFragment == null) {
                    jokerFragment = new JokersFragment();
                    transaction.add(R.id.fragmentLayout, jokerFragment, "JOKERFRAGMENT");
                    transaction.commit();
                } else {
                    transaction.remove(jokerFragment);
                    transaction.commit();
                }
                break;
        }
    }

    void goToAnsweredWrong() {
        Intent myAnsweredWrongIntent = new Intent(InGame.this, EndGame.class);
        InGame.this.startActivity(myAnsweredWrongIntent);
        resumeFunctionality = 404;
        finish();
    }

    private void setDefaultButton() {
        for (Button button : answerButtonsList) {
            button.setBackgroundResource(R.drawable.ingame_answer);
            button.setClickable(true);
        }
        jokerButton.setClickable(true);
    }

    private void setUnclickableButtons() {
        for (Button button : answerButtonsList) {
            button.setClickable(false);
        }
        jokerButton.setClickable(false);
    }

    @Override
    public void audienceVote(int vote) {
        Toast toast = Toast.makeText(this, getString(R.string.audience_toast) + " " + question.getAnswerList().get(vote), Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.audience_toast);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextColor(getResources().getColor(R.color.audienceToastTextColor));
        text.setTextSize(getResources().getDimension(R.dimen.audience_toast_text_size));
        toast.show();
    }

    @Override
    public void changeQuestion() {
        init();
    }

    @Override
    public void removeFragment() {
        JokersFragment jokerFragment = (JokersFragment) fragmentManager.findFragmentByTag("JOKERFRAGMENT");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(jokerFragment);
        transaction.commit();
    }
}
