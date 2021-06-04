package com.mamamoney.ussdrestservice;

import java.io.Serializable;

public class SessionModelId implements Serializable {

    private  String sessionId; 
    private  String msisdn;

    public SessionModelId(){}

    public SessionModelId(String sessionId, String msisdn) {
        this.sessionId = sessionId;
        this.msisdn = msisdn;
    }
}