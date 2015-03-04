package com.smart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.app42.paas.sdk.android.App42Exception;
import com.shephertz.app42.paas.sdk.android.game.Game;

import java.math.BigDecimal;

public class EndGame extends Activity implements App42ScoreService.App42ScoreWriter {
    ProgressDialog progressDialog;
    private EditText recordName;
    private FileManager fileManager = Menu.fileManager;
    private int score;
    private App42ScoreService asyncService;
    private Toast saveToast;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame);
        score = calculateScore(InGame.getAnsweredRightCounter());

        recordName = (EditText) findViewById(R.id.endgameRecordName);
        TextView lastQuestion = (TextView) findViewById(R.id.lastQuestion);
        TextView rightAnswer = (TextView) findViewById(R.id.rightAnswer);
        asyncService = App42ScoreService.instance();
        saveToast = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);

        if (InGame.resumeFunctionality == 404) {
            lastQuestion.setText(InGame.getCurrentQuestion());
            rightAnswer.setText(InGame.getCurrentRightAnswer());
            lastQuestion.setVisibility(View.VISIBLE);
            rightAnswer.setVisibility(View.VISIBLE);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.endgameSaveButton:
                if (recordName.getText().toString().matches("")) {
                    saveToast.setText("Въведи име!");
                    saveToast.show();
                } else {
                    String name = this.recordName.getText().toString();
                    saveScore(score, name);
                }
                break;
            case R.id.endgameMenuButton:
                InGame.ingameActivity.finish();
                finish();
                break;
        }
    }

    private int calculateScore(int rightAnswers) {
        int score;
        if (rightAnswers > 20) {
            score = (rightAnswers - 20) * 100 + 10 * 50 + 10 * 10;
        } else if (rightAnswers > 10) {
            score = (rightAnswers - 10) * 50 + 10 * 10;
        } else {
            score = rightAnswers * 10;
        }
        int bonus = 0;
        for (int i = 0; i < 3; i++) {
            if (JokersFragment.usedJokers[i] == 0) {
                bonus += score / 2;
            }
        }
        score += bonus;
        return score;
    }

    public void saveScore(int score, String name) {
        progressDialog = ProgressDialog.show(this, "", "Запазване на резултата..");
        progressDialog.setCancelable(true);
        fileManager.writeScoreFile(String.valueOf(score) + " - " + name);
        asyncService.saveScoreForUser(Constants.App42GameName, name, BigDecimal.valueOf(score), this);
    }

    @Override
    public void onSaveScoreSuccess(Game response) {
        progressDialog.dismiss();
        saveToast.setText("резултатът е качен");
        saveToast.show();
        InGame.ingameActivity.finish();
        finish();
    }

    @Override
    public void onSaveScoreFailed(App42Exception ex) {
        progressDialog.dismiss();
        saveToast.setText("запазено само локално");
        saveToast.show();
        InGame.ingameActivity.finish();
        finish();
    }
}
