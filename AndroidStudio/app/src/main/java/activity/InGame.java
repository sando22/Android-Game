package activity;

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

import fragment.JokersFragment;
import helper.Constants;
import manager.QuestionManager;
import model.Question;

public class InGame extends Activity implements JokersFragment.FragmentCommunicator {
    public static Activity ingameActivity;
    public static int resumeFunctionality = 0, answeredRightCounter = 0;
    public static Question question;
    public static ArrayList<Button> answerButtonsList = new ArrayList<>();
    private Handler handler = new Handler();
    private QuestionManager questionManager = Menu.quesstionManager;
    private TextView questionTextView;
    private ImageButton jokerButton;
    private Runnable runWrong = new Runnable() {
        public void run() {
            goToAnsweredWrong();
            setDefaultButton();
        }
    };
    private ProgressBar resultProgressBar;
    private Runnable runRight = new Runnable() {
        public void run() {
            init();
            setDefaultButton();
        }
    };
    private FragmentManager fragmentManager;

    public static String getCurrentQuestion() {
        return question.getQuestionTitle();
    }

    public static String getCurrentRightAnswer() {
        return question.getAnswersList().get(question.getCorrectAnswer());
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
        answerButtonsList.add((Button) findViewById(R.id.A));
        answerButtonsList.add((Button) findViewById(R.id.B));
        answerButtonsList.add((Button) findViewById(R.id.C));
        answerButtonsList.add((Button) findViewById(R.id.D));
        questionTextView = (TextView) findViewById(R.id.ingameQuestion);
        jokerButton = (ImageButton) findViewById(R.id.ingameJokerButton);
        resultProgressBar = (ProgressBar) findViewById(R.id.resultProgressBar);
        ingameActivity = this;
        fragmentManager = getFragmentManager();
        questionManager.generateQuestionList();
        int tag = 0;
        for (Button button : answerButtonsList) {
            button.setTag(tag);
            tag++;
        }
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
        JokersFragment.usedJokers = new int[3];
        answerButtonsList.clear();
    }

    protected void init() {
        if (questionManager.hasMoreQuestions() && answeredRightCounter < Constants.GameLength) {
            question = questionManager.getNextQuestion(answeredRightCounter / Constants.DifficultyStep);
            updateUI();
        } else {
            Intent myEndgameIntent = new Intent(InGame.this, EndGame.class);
            InGame.this.startActivity(myEndgameIntent);
            finish();
        }
    }

    private void updateUI() {
        questionTextView.setText(question.getQuestionTitle());
        ArrayList<String> answerList = question.getAnswersList();
        resultProgressBar.setProgress(answeredRightCounter);
        for (Button button : answerButtonsList) {
            button.setText(answerList.get(answerButtonsList.indexOf(button)));
            button.setEnabled(true);
        }
    }

    public void processAnswer(View v) {
        int keyID = (int) v.getTag();
        if (question.getCorrectAnswer() == keyID) {
            answerButtonsList.get(keyID).setBackgroundResource(R.drawable.ingame_answered_right);
            setUnclickableButtons();
            handler.postDelayed(runRight, 750);
            answeredRightCounter++;
        } else {
            answerButtonsList.get(keyID).setBackgroundResource(R.drawable.ingame_answered_wrong);
            setUnclickableButtons();
            handler.postDelayed(runWrong, 750);
        }
    }

    void goToAnsweredWrong() {
        Intent myAnsweredWrongIntent = new Intent(InGame.this, EndGame.class);
        InGame.this.startActivity(myAnsweredWrongIntent);
        resumeFunctionality = Constants.END_OF_GAME;
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
        Toast toast = Toast.makeText(this, getString(R.string.audience_toast) + " " + question.getAnswersList().get(vote), Toast.LENGTH_LONG);
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
    public void changeFragmentDisplay(View v) {
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
    }
}
