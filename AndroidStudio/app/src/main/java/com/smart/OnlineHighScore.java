package com.smart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.shephertz.app42.paas.sdk.android.App42Exception;
import com.shephertz.app42.paas.sdk.android.game.Game;

import java.math.BigDecimal;

public class OnlineHighScore extends Activity implements App42ScoreService.App42ScoreReader {

    TextView scoreText;
    private App42ScoreService asyncService;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlinehighscore);
        scoreText = (TextView) findViewById(R.id.onlineHighscoreScoreText);
        asyncService = App42ScoreService.instance();
        getScore();
    }

    public void getScore() {
        progressDialog = ProgressDialog.show(this, "", "Loading..");
        progressDialog.setCancelable(true);
        asyncService.getLeaderBoard(Constants.App42GameName, 15, this);
    }

    @Override
    public void onLeaderBoardSuccess(Game response) {
        progressDialog.dismiss();
        String text = "";
        for (int i = 0; i < response.getScoreList().size(); i++) {
            BigDecimal scoreValue = response.getScoreList().get(i).getValue();
            text += scoreValue.toString() + " - ";
            String name = response.getScoreList().get(i).getUserName();
            text += name + "\n";
        }
        scoreText.setText(text);
    }


    @Override
    public void onLeaderBoardFailed(App42Exception ex) {
        progressDialog.dismiss();
        scoreText.setText("Няма връзка с интернет!");
    }
}
