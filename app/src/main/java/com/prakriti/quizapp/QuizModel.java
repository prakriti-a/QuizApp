package com.prakriti.quizapp;

public class QuizModel {

    private int mQuestion;
    private boolean mAnswer;

    public QuizModel(int mQuestion, boolean mAnswer) {
        this.mQuestion = mQuestion;
        this.mAnswer = mAnswer;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int Question) {
        mQuestion = Question;
    }

    public boolean getAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean Answer) {
        mAnswer = Answer;
    }

}
