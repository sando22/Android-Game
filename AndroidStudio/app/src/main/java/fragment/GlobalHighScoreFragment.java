package fragment;

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

import App42Api.App42ServiceApi;
import activity.R;
import helper.Constants;

public class GlobalHighScoreFragment extends Fragment implements App42ServiceApi.App42ScoreReader {

    private final int SCORES_TO_SHOW = 10;
    TextView scoreText;
    private App42ServiceApi asyncService;
    private GlobalHighScoreFragmentCommunicator globalHighScoreFragmentCommunicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_global_high_score, container, false);
        scoreText = (TextView) view.findViewById(R.id.onlineHighscoreScoreText);
        asyncService = App42ServiceApi.instance();
        getScore();
        return view;
    }

    public void getScore() {
        globalHighScoreFragmentCommunicator.showLoadingPanel();
        asyncService.getLeaderBoard(Constants.App42GameName, SCORES_TO_SHOW, this);
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
        for (Game.Score gameScore : response.getScoreList()) {
            BigDecimal scoreValue = gameScore.getValue();
            text += scoreValue.toString() + " - ";
            String name = gameScore.getUserName();
            text += name + "\n";
        }
        scoreText.setText(text);
    }


    @Override
    public void onLeaderBoardFailed(App42Exception ex) {
        globalHighScoreFragmentCommunicator.hideLoadingPanel();
    }

    public interface GlobalHighScoreFragmentCommunicator {
        void showLoadingPanel();

        void hideLoadingPanel();
    }
}