package com.smart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.shephertz.app42.paas.sdk.android.App42Exception;
import com.shephertz.app42.paas.sdk.android.game.Game;

import java.math.BigDecimal;
import java.util.ArrayList;

public class LeaderboardSample extends Activity implements
        AsyncApp42ScoreServiceApi.App42ScoreBoardServiceListener {

    ListView list;
    private AsyncApp42ScoreServiceApi asyncService;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);
        list = (ListView) findViewById(R.id.leaderBoardList);
        asyncService = AsyncApp42ScoreServiceApi.instance();
    }

    public void onPreviousClicked(View view) {

    }

    public void onNextClicked(View view) {

    }

    public void onGetScoreClicked(View view) {
        progressDialog = ProgressDialog.show(this, "", "Loading..");
        progressDialog.setCancelable(true);
        asyncService.getLeaderBoard(Constants.App42GameName, 15, this);
    }

    public void onSaveScoreClicked(View view) {
        progressDialog = ProgressDialog.show(this, "", "Saving Score..");
        progressDialog.setCancelable(true);
        asyncService.saveScoreForUser(Constants.App42GameName, Constants.UserName, new BigDecimal(10000), this);
        //TODO da se izmisli kak shte stava ocenqvaneto
    }

    @Override
    public void onSaveScoreSuccess(Game response) {
        progressDialog.dismiss();
        createAlertDialog("Score SuccessFully Saved, For UserName : " + response.getScoreList().get(0).getUserName()
                + " With Score : " + response.getScoreList().get(0).getValue());
    }

    @Override
    public void onSaveScoreFailed(App42Exception ex) {
        progressDialog.dismiss();
        createAlertDialog("Exception Occurred : " + ex.getMessage());
    }

    @Override
    public void onLeaderBoardSuccess(Game response) {
        progressDialog.dismiss();
        ArrayList<String> rankList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> scoreList = new ArrayList<>();
        for (int i = 0; i < response.getScoreList().size(); i++) {
            int rank = i + 1;
            rankList.add(rank + "");
            String name = response.getScoreList().get(i).getUserName();
            nameList.add(name);
            BigDecimal scoreValue = response.getScoreList().get(i).getValue();
            scoreList.add(scoreValue + "");
        }
        LeaderboardAdapter adapter = new LeaderboardAdapter(LeaderboardSample.this, nameList, rankList, scoreList);
        list.setAdapter(adapter);
    }


    @Override
    public void onLeaderBoardFailed(App42Exception ex) {
        progressDialog.dismiss();
        createAlertDialog("Exception Occurred : " + ex.getMessage());
    }

    public void createAlertDialog(String msg) {
        AlertDialog.Builder alertBox = new AlertDialog.Builder(
                LeaderboardSample.this);
        alertBox.setTitle("Response Message");
        alertBox.setMessage(msg);
        alertBox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        alertBox.show();
    }


}
