/**
 * Created by Riku Pepponen on Feb 6, 2017.
 * With the help of Big Nerd Ranch
 */

package com.example.riku.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CheatActivity extends Activity {

    public static final String EXTRA_ANSWER_IS_TRUE = "com.example.riku.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.example.riku.geoquiz.answer_is_shown";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private Button mBackButton;
    private static final String TAG = "CheatActivity"; // for logging

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

        //Answer will not be shown until the user clicks the button
        setAnswerShownResult(false);

        // When the user clicks 'Show Answer', set the text to show the answer
        mShowAnswer = (Button)findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                    }
                setAnswerShownResult(true);
                }
        });

        // Back button
        mBackButton = (Button)findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}