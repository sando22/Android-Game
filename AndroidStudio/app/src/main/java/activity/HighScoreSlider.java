package activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import fragment.GlobalHighScoreFragment;
import fragment.HighScoreFragmentAdapter;
import fragment.LocalHighScoreFragment;

public class HighScoreSlider extends FragmentActivity implements
        LocalHighScoreFragment.LocalHighScoreFragmentCommunicator,
        GlobalHighScoreFragment.GlobalHighScoreFragmentCommunicator {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_swipe_view);
        ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
        HighScoreFragmentAdapter highScoreFragmentAdapter = new HighScoreFragmentAdapter(getSupportFragmentManager());

        viewpager.setAdapter(highScoreFragmentAdapter);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void showLoadingPanel() {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.highscoreslider_progressdialog_loading_message));
        progressDialog.setCancelable(true);
    }

    @Override
    public void hideLoadingPanel() {
        progressDialog.dismiss();
    }

    @Override
    public void resetLocalScores() {
        deleteFile(this.getString(R.string.filemanager_ScoresFile));
        finish();
    }
}
