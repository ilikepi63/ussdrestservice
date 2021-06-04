package com.mamamoney.ussdrestservice;

import java.math.RoundingMode;
import java.math.BigDecimal;

class StepTwo implements Step<Double>{

    private final String sessionId;
    private final String msisdn;
    private static int STEP_NUMBER = 2;

    private SessionStepsRepository stepRepository;
    private SessionAnswersRepository sessionAnswersRepository;

    StepOne stepOne;

    StepTwo(String sessionId, String msisdn, SessionStepsRepository stepRepository, SessionAnswersRepository sessionAnswersRepository, StepOne stepOne){
        this.sessionId = sessionId;
        this.msisdn = msisdn;
        this.stepRepository = stepRepository;
        this.sessionAnswersRepository = sessionAnswersRepository;
        this.stepOne = stepOne;
    };

    static int getStepNumber(){
        return STEP_NUMBER;
    }

    @Override
    public Double getAnswer(){
        SessionAnswersModel answer = this.sessionAnswersRepository.findById(new SessionAnswersModelId(this.sessionId, this.msisdn, STEP_NUMBER)).get();

        return answer.getValue();
    };

    @Override
    public String getQuestion(){
        SessionStepsModel step = stepRepository.findById(STEP_NUMBER).get();

        CountryModel country = stepOne.getAnswer();

        // TODO: check for null on country

        return  String.format(step.getQuestion(), country.getCountryName());
    };

    @Override
    public boolean validateAnswer(double answer){
        return true;
    };

    @Override
    public void saveAnswer(double answer){

        // saving it for precision
        BigDecimal bd = BigDecimal.valueOf(answer);
        bd = bd.setScale(2, RoundingMode.FLOOR); 

        sessionAnswersRepository.save(new SessionAnswersModel(sessionId, msisdn, STEP_NUMBER,  bd.doubleValue()));
    };

};