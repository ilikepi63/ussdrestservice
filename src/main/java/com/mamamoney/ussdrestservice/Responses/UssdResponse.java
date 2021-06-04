package com.mamamoney.ussdrestservice;

public class UssdResponse{
    private final String sessionId; 
    private final String message;

    public UssdResponse(String sessionId, String message){
        this.sessionId = sessionId;
        this.message = message;
    };

    //getters
    public String getSessionId(){
        return this.sessionId;
    }

    public String getMessage(){
        return this.message;
    }
    
}