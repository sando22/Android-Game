package com.smart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shephertz.app42.paas.sdk.android.App42Exception;
import com.shephertz.app42.paas.sdk.android.storage.Storage;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionSubmit extends Activity implements App42ServiceApi.App42StorageServiceListener {
    EditText question;
    EditText rightanswer;
    EditText answer2;
    EditText answer3;
    EditText answer4;
    ProgressDialog progressDialog;
    private Toast questionSubmitToast;
    private int difficulty = -1;
    private App42ServiceApi app42ServiceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionsubmit);

        question = (EditText) findViewById(R.id.questionsubmitQuestion);
        rightanswer = (EditText) findViewById(R.id.questionsubmitanswer1);
        answer2 = (EditText) findViewById(R.id.questionsubmitanswer2);
        answer3 = (EditText) findViewById(R.id.questionsubmitanswer3);
        answer4 = (EditText) findViewById(R.id.questionsubmitanswer4);
        questionSubmitToast = Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT);
        app42ServiceApi = App42ServiceApi.instance();

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
            progressDialog = ProgressDialog.show(this, "", "Изпращане");
            progressDialog.setCancelable(true);
            JSONObject jsonToSave = new JSONObject();
            try {
                jsonToSave.put("Question", question.getText().toString());
                jsonToSave.put("Difficulty", String.valueOf(difficulty));
                jsonToSave.put("Right answer", rightanswer.getText().toString());
                jsonToSave.put("Answer 2", answer2.getText().toString());
                jsonToSave.put("Answer 3", answer3.getText().toString());
                jsonToSave.put("Answer 4", answer4.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            app42ServiceApi.insertJSONDoc(Constants.dbName, Constants.collectionName, jsonToSave, this);
        } else {
            questionSubmitToast.setText(R.string.questionsubmit_fielderror_toast);
            questionSubmitToast.show();
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

    @Override
    public void onDocumentInserted(Storage response) {
        progressDialog.dismiss();
        questionSubmitToast.setText(R.string.questionsubmit_success_toast);
        questionSubmitToast.show();
        finish();
    }

    @Override
    public void onInsertionFailed(App42Exception ex) {
        progressDialog.dismiss();
        questionSubmitToast.setText(R.string.questionsubmit_senderror_toast);
        questionSubmitToast.show();
        createAlertDialog("Exception Occurred : " + ex.getMessage());
    }

    public void createAlertDialog(String msg) {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(
                QuestionSubmit.this);
        alertbox.setTitle("Response Message");
        alertbox.setMessage(msg);
        alertbox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        alertbox.show();
    }
}
