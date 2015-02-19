package com.smart;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class LocalHighScoreFragment extends Fragment {

    private FileManager fileManager = Menu.fileManager;
    private LocalHighScoreFragmentCommunicator localHighScoreFragmentCommunicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_high_score, container, false);
        TextView scoreText = (TextView) view.findViewById(R.id.highscoreScoreText);
        scoreText.setText(sortResult(fileManager.readScoreFile()));
        Button resetButton = (Button) view.findViewById(R.id.highscoreDeleteButton);

        resetButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        localHighScoreFragmentCommunicator.resetLocalScores();
                    }
                });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        localHighScoreFragmentCommunicator = (LocalHighScoreFragmentCommunicator) activity;
    }

    private String sortResult(ArrayList<String> lines) {
        HighScoreComparator cmp = new HighScoreComparator();
        String stringToPost = "";

        Collections.sort(lines, cmp);
        if (lines.size() > 10) {
            for (int i = lines.size() - 1; i > 9; i--) {
                lines.remove(i);
            }
        }
        for (String string : lines) {
            stringToPost += string + "\n";
        }
        return stringToPost;
    }

    interface LocalHighScoreFragmentCommunicator {
        public void resetLocalScores();
    }
}