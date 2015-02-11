package com.smart;

import android.os.Handler;

import com.shephertz.app42.paas.sdk.android.App42Exception;
import com.shephertz.app42.paas.sdk.android.ServiceAPI;
import com.shephertz.app42.paas.sdk.android.game.Game;
import com.shephertz.app42.paas.sdk.android.game.ScoreBoardService;

import java.math.BigDecimal;

public class App42ScoreService {
    private static App42ScoreService myInstance = null;
    private ScoreBoardService scoreBoardService;

    private App42ScoreService() {
        ServiceAPI serviceAPI = new ServiceAPI(Constants.App42ApiKey,
                Constants.App42ApiSecret);
        this.scoreBoardService = serviceAPI.buildScoreBoardService();
    }

    public static App42ScoreService instance() {
        if (myInstance == null) {
            myInstance = new App42ScoreService();
        }
        return myInstance;
    }

    public void saveScoreForUser(final String gameName,
                                 final String gameUserName, final BigDecimal gameScore, final App42ScoreWriter callBack) {
        final Handler callerThreadHandler = new Handler();
        new Thread() {
            @Override
            public void run() {
                try {
                    final Game response = scoreBoardService.saveUserScore(gameName, gameUserName, gameScore);
                    callerThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSaveScoreSuccess(response);
                        }
                    });
                } catch (final App42Exception ex) {
                    callerThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.onSaveScoreFailed(ex);
                            }
                        }
                    });
                }
            }
        }.start();
    }

    public void getLeaderBoard(final String gameName,
                               final int max, final App42ScoreReader callBack) {
        final Handler callerThreadHandler = new Handler();
        new Thread() {
            @Override
            public void run() {
                try {
                    final Game response = scoreBoardService.getTopNRankers(gameName, max);
                    callerThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onLeaderBoardSuccess(response);
                        }
                    });
                } catch (final App42Exception ex) {
                    callerThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.onLeaderBoardFailed(ex);
                            }
                        }
                    });
                }
            }
        }.start();
    }

    public static interface App42ScoreWriter {
        void onSaveScoreSuccess(Game response);

        void onSaveScoreFailed(App42Exception ex);
    }

    public static interface App42ScoreReader {
        void onLeaderBoardSuccess(Game response);

        void onLeaderBoardFailed(App42Exception ex);
    }
}