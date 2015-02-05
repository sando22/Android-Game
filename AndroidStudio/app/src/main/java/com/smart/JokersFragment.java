package com.smart;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

public class JokersFragment extends Fragment {
    private FragmentCommunicator fragmentCommunicator;

    int rightAnswerNumber;
    int i, j;
    static int[] usedJokers = new int[3];
    Random random = new Random();
    Button fiftyButton, changeButton, audienceButton;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentCommunicator = (FragmentCommunicator) activity;
    }

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

        return view;
    }

    private void setButtonsGraphic() {
        for (int k = 0; k < 3; k++) {
            if (usedJokers[k] == 1) {
                switch (k) {
                    case 0:
                        audienceButton.setBackgroundResource(R.drawable.joker_buttons_used);
                        audienceButton.setEnabled(false);
                        break;
                    case 1:
                        changeButton.setBackgroundResource(R.drawable.joker_buttons_used);
                        changeButton.setEnabled(false);
                        break;
                    case 2:
                        fiftyButton.setBackgroundResource(R.drawable.joker_buttons_used);
                        fiftyButton.setEnabled(false);
                        break;
                }
            }
        }
    }

    private void audienceWork() {
        usedJokers[0] = 1;
        if (random.nextInt(99) < 80) {
            fragmentCommunicator.audienceVote(rightAnswerNumber);
        } else {
            int i;
            do {
                i = random.nextInt(4);
            } while (i == rightAnswerNumber);
            fragmentCommunicator.audienceVote(i);
        }
        fragmentCommunicator.removeFragment();
    }

    private void changeWork() {
        InGame.resumeFunctionality = 0;
        usedJokers[1] = 1;
        fragmentCommunicator.changeQuestion();
        fragmentCommunicator.removeFragment();
    }

    private void fiftyWork() {
        do {
            i = random.nextInt(4);
            j = random.nextInt(4);
        } while (i == rightAnswerNumber || j == rightAnswerNumber || i == j);
        disableAnswerButtons(i);
        disableAnswerButtons(j);
        usedJokers[2] = 1;
        fragmentCommunicator.removeFragment();
    }

    private void disableAnswerButtons(int but) {
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

    interface FragmentCommunicator {
        public void audienceVote(int vote);

        public void changeQuestion();

        public void removeFragment();
    }
}
