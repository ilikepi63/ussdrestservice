package com.mamamoney.ussdrestservice;

public class UssdRequest{
    private final String sessionId; 
    private final String msisdn;
    private final double userEntry;

    public UssdRequest(String sessionId, String msisdn, double userEntry){
        this.sessionId = sessionId;
        this.msisdn = msisdn;
        this.userEntry = userEntry;
    };

    //getters
    public String getSessionId(){
        return this.sessionId;
    }

    public String getMsisdn(){
        return this.msisdn;
    }

    public double getUserEntry(){
        return this.userEntry;
    }
    
}