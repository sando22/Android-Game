package com.smart;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shephertz.app42.paas.sdk.android.App42Exception;
import com.shephertz.app42.paas.sdk.android.game.Game;

import java.math.BigDecimal;

public class GlobalHighScoreFragment extends Fragment implements App42ScoreService.App42ScoreReader {

    TextView scoreText;
    private App42ScoreService asyncService;
    private GlobalHighScoreFragmentCommunicator globalHighScoreFragmentCommunicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_global_high_score, container, false);
        scoreText = (TextView) view.findViewById(R.id.onlineHighscoreScoreText);
        asyncService = App42ScoreService.instance();
        getScore();
        return view;
    }

    public void getScore() {
        globalHighScoreFragmentCommunicator.showLoadingPanel();
        asyncService.getLeaderBoard(Constants.App42GameName, 10, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        globalHighScoreFragmentCommunicator = (GlobalHighScoreFragmentCommunicator) activity;
    }

    @Override
    public void onLeaderBoardSuccess(Game response) {
        globalHighScoreFragmentCommunicator.hideLoadingPanel();
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
        globalHighScoreFragmentCommunicator.hideLoadingPanel();
        scoreText.setText("Няма връзка с интернет!");
    }

    interface GlobalHighScoreFragmentCommunicator {
        public void showLoadingPanel();

        public void hideLoadingPanel();
    }
}