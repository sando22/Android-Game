package com.smart;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class QuestionSubmit extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionsubmit);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void sendQuestion(View v) {
        Log.i("Button test", "Button is pressed !!");
        //todo
    }
}
