package com.mamamoney.ussdrestservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.IdClass;

@Entity
@Table(name = "session")
@IdClass(SessionModelId.class)
public class SessionModel {

    @Id
    @Column(name = "session_id", nullable = false)
    private  String sessionId; 

    @Id
    @Column(name = "msisdn", nullable = false)
    private  String msisdn;

    @Column(name = "current_step", nullable = true)
    private  int currentStep;

    public SessionModel(){}

    public SessionModel(String sessionId, String msisdn){
        this.sessionId = sessionId;
        this.msisdn = msisdn;
    };

    public String getSessionId(){
        return sessionId;
    }

    public String getMsisdn(){
        return msisdn;
    }

    public int getCurrentStep(){
        return currentStep;
    }

    public SessionModel setSessionId(String sessionId){
        this.sessionId = sessionId;
        return this;
    }

    public SessionModel setMsisdn(String msisdn){
        this.msisdn = msisdn;
        return this;
    }

    public SessionModel setCurrentStep(int currentStep){
        this.currentStep = currentStep;
        return this;
    }


    public SessionModel incrementCurrentStep(){
        this.currentStep = currentStep + 1;
        return this;
    }

}