/**
 * Created by Riku Pepponen on Feb 6, 2017.
 * Updated Feb 7, 2017
 * With the help of Big Nerd Ranch
 */

package com.example.riku.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CheatActivity extends Activity {

    public static final String EXTRA_ANSWER_IS_TRUE = "com.example.riku.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.example.riku.geoquiz.answer_is_shown";
    public static final String KEY_PLAYER_CHEATS = "com.example.riku.geoquiz.player_cheats";

    private boolean mAnswerIsTrue;
    private boolean mIsCheater;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private Button mBackButton;
    private static final String TAG = "CheatActivity"; // for logging

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    // Function declaration for showing the correct answer in AnswerTextView
    private void setAnswerTextView(boolean isTrue) {
        if (isTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // The user hasn't cheated yet
        mIsCheater = false;

        //Get the correct answer (default is false, shouldn't happen)
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        // Initialize TextView
        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

            // When the user clicks 'Show Answer', set the AnswerTextView to show the answer
            // Also remember that the user is a dirty cheater
            mShowAnswer = (Button)findViewById(R.id.show_answer_button);
            mShowAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswerTextView(mAnswerIsTrue);
                    setAnswerShownResult(true);
                    mIsCheater = true;
                }
            });

        // If the device orientation changes, retain the original cheater value and the answer text
        if (savedInstanceState != null) {
            mIsCheater = savedInstanceState.getBoolean(KEY_PLAYER_CHEATS);
            setAnswerShownResult(true);
            if (mIsCheater) {
                setAnswerTextView(mAnswerIsTrue);
            }
        } else {
            setAnswerShownResult(false);
        }

        // Back button
        mBackButton = (Button)findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // Allows passing the cheater value if the device orientation changes
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putBoolean(KEY_PLAYER_CHEATS, mIsCheater);
    }
}