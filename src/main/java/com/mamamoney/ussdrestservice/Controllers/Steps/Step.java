package com.mamamoney.ussdrestservice;

interface Step<T>{

    public T getAnswer();

    public String getQuestion();

    public boolean validateAnswer(double answer);

    public void saveAnswer(double answer);

}