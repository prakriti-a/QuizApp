package com.prakriti.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtQuestion, txtQuizStats;
    private Button btnTrue, btnFalse;
    private ProgressBar pbQuiz;

    private int questionIndex, quizQuestion, userScore;

    private QuizModel[] questionCollection = new QuizModel[] {
            new QuizModel(R.string.q1, true),
            new QuizModel(R.string.q2, false),
            new QuizModel(R.string.q3, true),
            new QuizModel(R.string.q4, true),
            new QuizModel(R.string.q5, true),
            new QuizModel(R.string.q6, false),
            new QuizModel(R.string.q7, false),
            new QuizModel(R.string.q8, true),
            new QuizModel(R.string.q9, false),
            new QuizModel(R.string.q10, true),
    };

        // can't use a variable before creating it
    private final int USER_PROGRESS = (int) Math.ceil(100.0 / questionCollection.length); // max value of progress bar is 100
        // ceil returns a double val which is cast to int

    private final String SCORE_KEY = "SCORE";
    private final String INDEX_KEY = "INDEX";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            // if orientation is changed, the parameter won't be null bcoz of overidden onSaveInstanceState method
        if(savedInstanceState != null) {
            userScore = savedInstanceState.getInt(SCORE_KEY);
            questionIndex = savedInstanceState.getInt(INDEX_KEY);
        }
        else {
            userScore = 0;
            questionIndex = 0;
        }

        txtQuestion = findViewById(R.id.txtQuestion);
        txtQuizStats = findViewById(R.id.txtQuizStats);
        pbQuiz = findViewById(R.id.pbQuiz);

        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);
        btnTrue.setOnClickListener(this);
        btnFalse.setOnClickListener(this);

            // to display first question
        QuizModel q1 = questionCollection[questionIndex]; // index = 0
        quizQuestion = q1.getQuestion();
        txtQuestion.setText(quizQuestion);

        txtQuizStats.setText("YOUR SCORE: " + userScore);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTrue:
                evaluateAnswer(true);
                changeQuestionOnButtonClick();
                break;
            case R.id.btnFalse:
                evaluateAnswer(false);
                changeQuestionOnButtonClick();
                break;
        }
    }

    private void changeQuestionOnButtonClick() {
        Log.i("MYINDEX", questionIndex + "");

            // check if end of quiz is reached
        if(questionIndex == questionCollection.length - 1) {
            questionIndex = 0;
            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
            quizAlert.setCancelable(false);
            quizAlert.setTitle("The Quiz Has Ended!");
            quizAlert.setMessage("YOUR FINAL SCORE IS: " + userScore);
            quizAlert.setPositiveButton("Close App", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            quizAlert.show();
        }
        else {
            // to display subsequent questions by incrementing index
            questionIndex += 1;
            // using % 10 loops through question array, displays first ques after last ques on button click
            quizQuestion = questionCollection[questionIndex].getQuestion();
            txtQuestion.setText(quizQuestion);
            // to show user's progress on each button click
            pbQuiz.incrementProgressBy(USER_PROGRESS); // accepts int val
            txtQuizStats.setText("YOUR SCORE: " + userScore); // accepts string or Resource ID
        }
    }

    private void evaluateAnswer(boolean guess) {
        boolean currentQuesAnswer = questionCollection[questionIndex].getAnswer();
        if(currentQuesAnswer == guess) {
            Toast.makeText(this, R.string.correct_toast_message, Toast.LENGTH_SHORT).show();
            userScore += 1;
        }
        else
            Toast.makeText(this, R.string.incorrect_toast_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE_KEY, userScore);
        outState.putInt(INDEX_KEY, questionIndex);
    }
}