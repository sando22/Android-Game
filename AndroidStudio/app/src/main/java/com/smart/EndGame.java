package com.smart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EndGame extends Activity {
    private EditText recordName;
    private FileManager fileManager = Menu.fileManager;
    private int score;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame);
        score = InGame.getRightAnswersCounter();

        recordName = (EditText) findViewById(R.id.endgameRecordName);
        TextView lastQuestion = (TextView) findViewById(R.id.lastQuestion);
        TextView rightAnswer = (TextView) findViewById(R.id.rightAnswer);

        if (InGame.resumeFunctionality == 404) {
            lastQuestion.setText(InGame.getCurrentQuestion());
            rightAnswer.setText(InGame.getCurrentRightAnswer());
            lastQuestion.setVisibility(View.VISIBLE);
            rightAnswer.setVisibility(View.VISIBLE);
        }
    }

    public void onClick(View v) {
        Toast saveToast = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);
        switch (v.getId()) {
            case R.id.endgameSaveButton:
                if (recordName.getText().toString().matches("")) {
                    saveToast.setText("Въведи име!");
                    saveToast.show();
                } else {
                    fileManager.writeScoreFile(String.valueOf(score) + " - " + recordName.getText().toString());
                    saveToast.setText("запазено");
                    saveToast.show();
                    InGame.ingameActivity.finish();
                    finish();
                }
                break;
            case R.id.endgameMenuButton:
                InGame.ingameActivity.finish();
                finish();
                break;
        }
    }
}
