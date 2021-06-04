package com.mamamoney.ussdrestservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.IdClass;

// Session Steps entity is a hybrid entity comprising both steps as well
// as options for steps that are an enum type. Steps intrinsically hold their own 
// validation values.

// this goes against the principles of normalization, but will save us having to 
// make a join at runtime for the options of a specific step

@Entity
@Table(name = "session_steps")
public class SessionStepsModel {

    @Id
    @Column(name = "step_number", nullable = false)
    private Integer stepNumber; 

    @Column(name = "question", nullable = false)
    private String question;

    public SessionStepsModel(){}

    public SessionStepsModel(int stepNumber, String question){
        this.stepNumber = stepNumber;
        this.question = question;
    };

    // getters
    public int getStepNumber(){
        return stepNumber;
    }

    public String getQuestion(){
        return question;
    }

    // setters
    public SessionStepsModel setStepNumber(int stepNumber){
        this.stepNumber = stepNumber;
        return this;
    }

}