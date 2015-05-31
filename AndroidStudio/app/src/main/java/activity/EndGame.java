package activity;

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

import App42Api.App42ServiceApi;
import helper.Constants;
import manager.FileManager;

public class EndGame extends Activity implements App42ServiceApi.App42ScoreWriter {
    private ProgressDialog progressDialog;
    private EditText recordName;
    private FileManager fileManager = Menu.fileManager;
    private int score;
    private App42ServiceApi asyncService;
    private Toast saveToast;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame);
        score = calculateScore(InGame.getAnsweredRightCounter());

        recordName = (EditText) findViewById(R.id.endgameRecordName);
        TextView lastQuestion = (TextView) findViewById(R.id.lastQuestion);
        TextView rightAnswer = (TextView) findViewById(R.id.rightAnswer);
        asyncService = App42ServiceApi.instance();
        saveToast = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);

        if (InGame.resumeFunctionality == Constants.END_OF_GAME) {
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
                    saveToast.setText(R.string.endgame_toast_on_empty_name);
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
        if (rightAnswers >= Constants.DifficultyStep * 2) {
            score = (rightAnswers - Constants.DifficultyStep * 2) * 100 + Constants.DifficultyStep * 50 + Constants.DifficultyStep * 20;
        } else if (rightAnswers >= Constants.DifficultyStep) {
            score = (rightAnswers - Constants.DifficultyStep) * 50 + Constants.DifficultyStep * 20;
        } else if (rightAnswers > 0) {
            score = rightAnswers * 20;
        } else {
            score = 0;
        }
        return score;
    }

    public void saveScore(int score, String name) {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.endgame_progressdialog_saving_message));
        progressDialog.setCancelable(true);
        fileManager.writeScoreFile(String.valueOf(score) + " - " + name);
        asyncService.saveScoreForUser(Constants.App42GameName, name, BigDecimal.valueOf(score), this);
    }

    @Override
    public void onSaveScoreSuccess(Game response) {
        progressDialog.dismiss();
        saveToast.setText(R.string.endgame_toast_on_saving_success);
        saveToast.show();
        InGame.ingameActivity.finish();
        finish();
    }

    @Override
    public void onSaveScoreFailed(App42Exception ex) {
        progressDialog.dismiss();
        saveToast.setText(R.string.endgame_toast_on_saving_success);
        saveToast.show();
        InGame.ingameActivity.finish();
        finish();
    }
}
