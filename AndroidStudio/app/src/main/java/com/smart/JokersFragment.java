package com.smart;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class JokersFragment extends Fragment {

    Toast helpToast;
    int rightAnswerNumber;
    int i, j;
    static int[] usedJokers = new int[3];
    Random random = new Random();
    Button fiftyButton, changeButton, audienceButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jokerfragment, container, false);

        fiftyButton = (Button) view.findViewById(R.id.fragmentFiftyButton);
        changeButton = (Button) view.findViewById(R.id.fragmentChangeButton);
        audienceButton = (Button) view.findViewById(R.id.fragmentAudienceButton);

        setButtonsGraphic();
        rightAnswerNumber = InGame.getCurrentRightAnswerNumber();

        audienceButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        audienceWork();
                    }
                });
        fiftyButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        fiftyWork();
                    }
                });
        changeButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        changeWork();
                    }
                });

        helpToast = Toast.makeText(getActivity(), "aaa", Toast.LENGTH_SHORT);

        return view;
    }

    private void setButtonsGraphic() {
        for (int k = 0; k < 3; k++) {
            if (usedJokers[k] == 1) {
                switch (k) {
                    case 0:
//                        audienceButton.setBackgroundResource(R.drawable.joker_public_gg);
                        audienceButton.setEnabled(false);
                        break;
                    case 1:
//                        changeButton.setBackgroundResource(R.drawable.joker_change_gg);
                        changeButton.setEnabled(false);
                        break;
                    case 2:
//                        fiftyButton.setBackgroundResource(R.drawable.joker_50_gg);
                        fiftyButton.setEnabled(false);
                        break;
                }
            }
        }
    }

    private void audienceWork() {
        helpToast.setText("Audience voted mostly for " + InGame.getCurrentRightAnswer());
        helpToast.show();
        usedJokers[0] = 1;
    }

    private void changeWork() {
        helpToast.setText("Answer changed");
        helpToast.show();
        InGame.resumeFunctionality = 0;
        usedJokers[1] = 1;
    }

    private void fiftyWork() {
        do {
            i = random.nextInt(4);
            j = random.nextInt(4);
        } while (i == rightAnswerNumber || j == rightAnswerNumber || i == j);
        disableButtons(i);
        disableButtons(j);
        usedJokers[2] = 1;
    }

    private void disableButtons(int but) {
        switch (but) {
            case 0:
                InGame.answerButton1.setEnabled(false);
                break;
            case 1:
                InGame.answerButton2.setEnabled(false);
                break;
            case 2:
                InGame.answerButton3.setEnabled(false);
                break;
            case 3:
                InGame.answerButton4.setEnabled(false);
                break;
        }
    }
}
