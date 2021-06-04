package com.mamamoney.ussdrestservice;

import java.io.Serializable;

public class SessionAnswersModelId implements Serializable {

    private  String sessionId; 
    private  String msisdn;
    private int stepNumber;

    public SessionAnswersModelId(){}

    public SessionAnswersModelId(String sessionId, String msisdn, int stepNumber) {
        this.sessionId = sessionId;
        this.msisdn = msisdn;
        this.stepNumber = stepNumber;
    }
}