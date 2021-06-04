package com.mamamoney.ussdrestservice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.IdClass;

@Entity
@Table(name = "session_answers")
@IdClass(SessionAnswersModelId.class)
public class SessionAnswersModel {

    @Id
    @Column(name = "session_id", nullable = false)
    private  String sessionId; 

    @Id
    @Column(name = "msisdn", nullable = false)
    private  String msisdn;

    @Id
    @Column(name = "step_number", nullable = false)
    private  int stepNumber;

    @Column(name = "value", nullable = false, precision=10, scale=2)
    private double value;

    public SessionAnswersModel() {}

    public SessionAnswersModel(String sessionId, String msisdn, int stepNumber, double value) {
        this.sessionId = sessionId;
        this.msisdn = msisdn;
        this.stepNumber = stepNumber;
        this.value = value;
    }

    public double getValue(){
        return this.value;
    }

}