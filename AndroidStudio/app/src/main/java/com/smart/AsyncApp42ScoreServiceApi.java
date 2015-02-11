package com.smart;

import android.os.Handler;

import com.shephertz.app42.paas.sdk.android.App42Exception;
import com.shephertz.app42.paas.sdk.android.ServiceAPI;
import com.shephertz.app42.paas.sdk.android.game.Game;
import com.shephertz.app42.paas.sdk.android.game.ScoreBoardService;

import java.math.BigDecimal;

public class AsyncApp42ScoreServiceApi {
    private static AsyncApp42ScoreServiceApi mInstance = null;
    private ScoreBoardService scoreBoardService;

    private AsyncApp42ScoreServiceApi() {
        ServiceAPI sp = new ServiceAPI(Constants.App42ApiKey,
                Constants.App42ApiSecret);
        this.scoreBoardService = sp.buildScoreBoardService();
    }

    public static AsyncApp42ScoreServiceApi instance() {
        if (mInstance == null) {
            mInstance = new AsyncApp42ScoreServiceApi();
        }
        return mInstance;
    }

    public void saveScoreForUser(final String gameName,
                                 final String gameUserName, final BigDecimal gameScore, final App42ScoreBoardServiceListener callBack) {
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
                               final int max, final App42ScoreBoardServiceListener callBack) {
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

    public static interface App42ScoreBoardServiceListener {
        void onSaveScoreSuccess(Game response);

        void onSaveScoreFailed(App42Exception ex);

        void onLeaderBoardSuccess(Game response);

        void onLeaderBoardFailed(App42Exception ex);
    }

}