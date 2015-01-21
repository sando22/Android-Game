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

        recordName = (EditText) findViewById(R.id.endgameRecordName);
        TextView lastQuestion = (TextView) findViewById(R.id.lastQuestion);

        if (InGame.resumeFunctionality == 404) {
            lastQuestion.setText(InGame.getCurrentQuestion());
            lastQuestion.setVisibility(View.VISIBLE);
            score = InGame.getRightAnswersCounter();
            InGame.ingameActivity.finish();
        }
    }

    public void onClick(View v) {
        Toast saveToast = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);
        switch (v.getId()) {
            case R.id.endgameSaveButton:
                if (recordName.getText().toString().matches("")) {
                    saveToast.setText("Can't save without name!");
                    saveToast.show();
                } else {
                    fileManager.writeScoreFile(String.valueOf(score) + " - " + recordName.getText().toString());
                    saveToast.setText("saved");
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
