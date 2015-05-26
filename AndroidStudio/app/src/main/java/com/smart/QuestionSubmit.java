package com.smart;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class QuestionSubmit extends Activity {
    EditText question;
    EditText rightanswer;
    EditText answer2;
    EditText answer3;
    EditText answer4;
    private Toast errorToast;
    private int difficulty = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionsubmit);

        question = (EditText) findViewById(R.id.questionsubmitQuestion);
        rightanswer = (EditText) findViewById(R.id.questionsubmitanswer1);
        answer2 = (EditText) findViewById(R.id.questionsubmitanswer2);
        answer3 = (EditText) findViewById(R.id.questionsubmitanswer3);
        answer4 = (EditText) findViewById(R.id.questionsubmitanswer4);
        errorToast = Toast.makeText(getBaseContext(),R.string.questionsubmit_error_toast, Toast.LENGTH_SHORT);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void radioClicked(View v) {
        switch (v.getId()) {
            case R.id.questionsubmitRadioButton1:
                difficulty = 0;
                break;
            case R.id.questionsubmitRadioButton2:
                difficulty = 1;
                break;
            case R.id.questionsubmitRadioButton3:
                difficulty = 2;
                break;
        }
    }

    public void sendQuestion(View v) {
        if (isValid()) {
            Log.i("test", "It's valid!");
        } else {
           errorToast.show();
        }
    }

    public Boolean isValid() {
        return !(difficulty == -1
                || question.getText().toString().equals("")
                || rightanswer.getText().toString().equals("")
                || answer2.getText().toString().equals("")
                || answer3.getText().toString().equals("")
                || answer4.getText().toString().equals(""));
    }
}
