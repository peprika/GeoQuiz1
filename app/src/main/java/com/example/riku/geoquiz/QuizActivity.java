/**
 * Created by Riku Pepponen on Feb 6, 2017.
 * With the help of Big Nerd Ranch
 */

package com.example.riku.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class QuizActivity extends AppCompatActivity {

    // Some basic declarations
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private boolean mIsCheater;
    private int mCurrentIndex = 0; // current page
    private static final String TAG = "QuizActivity"; // for logging
    private static final String KEY_INDEX = "index";

    // Question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question1, true),
            new TrueFalse(R.string.question2, false),
            new TrueFalse(R.string.question3, false),
            new TrueFalse(R.string.question4, true),
            new TrueFalse(R.string.question5, false),
    };

    // "Update the question" function
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    // Check the answer and show the appropriate toast
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResId;
        if (mIsCheater) {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.judgement_toast;
                }
            else {
                messageResId = R.string.fail_toast;
            }
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
                }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreateBundle() called");
        setContentView(R.layout.activity_quiz);

        // Allow the text view to behave as the Next button
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
            });

        // True/False buttons check the answer
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                checkAnswer(true);
             }
         });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        // Next button: go to next question and reset the cheater
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                        mIsCheater = false;
                updateQuestion();
            }
        });

        //Previous button: go to the previous question
        mPreviousButton = (ImageButton)findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                    updateQuestion(); }
                else {
                    mCurrentIndex = (mCurrentIndex - 1);
                    updateQuestion(); }
                }
            });

        // Cheat button starts CheatActivity and passes the current index
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);

                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);

                startActivityForResult(i, 0);
            }
        });

        // If the device orientation changes, get the original mCurrentIndex
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

    }
        updateQuestion();
    }

    // Allows passing the mCurrentIndex value if the device orientation changes
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    // The following methods are used for logging activity statuses
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onStart();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
        super.onStart();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
        super.onStart();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onStart();
        Log.d(TAG, "onDestroy() called");
    }
}