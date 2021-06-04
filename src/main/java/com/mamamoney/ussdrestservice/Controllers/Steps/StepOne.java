package com.mamamoney.ussdrestservice;

import java.util.List;

class StepOne implements Step<CountryModel>{

    private  String sessionId;
    private  String msisdn;
    private  SessionStepsRepository stepRepository;
    private  CountryRepository countryRepository;
    SessionAnswersRepository sessionAnswersRepository;
    private static int STEP_NUMBER = 1;

    StepOne(String sessionId, String msisdn, SessionStepsRepository stepRepository, CountryRepository countryRepository, SessionAnswersRepository sessionAnswersRepository){
        this.sessionId = sessionId;
        this.msisdn = msisdn;
        this.stepRepository = stepRepository;
        this.countryRepository = countryRepository;
        this.sessionAnswersRepository = sessionAnswersRepository;
    }

    static int getStepNumber(){
        return STEP_NUMBER;
    }


    @Override
    public CountryModel getAnswer(){

        // TODO null pointer checks

        SessionAnswersModel answer = this.sessionAnswersRepository.findById(new SessionAnswersModelId(this.sessionId, this.msisdn, STEP_NUMBER)).get();

        Long answerValue = (long) answer.getValue();

        return countryRepository.findById(answerValue).get();
    };

    @Override
    public String getQuestion(){

        SessionStepsModel step = stepRepository.findById(STEP_NUMBER).get();

        List<CountryModel> countries = countryRepository.findAll();

        String question = step.getQuestion();

        for(int i = 0; i < countries.size(); i++){

            CountryModel country = countries.get(i);

            String option = "\n" + Long.toString(country.getId()) + ") " + country.getCountryName();

            question = question + option;
        }

        return question;
    };

    @Override
    public boolean validateAnswer(double answer){

        List<CountryModel> countries = countryRepository.findAll();

        for(int i = 0; i < countries.size(); i++){

            CountryModel country = countries.get(i);

            if(country.getId() == Double.valueOf(answer).longValue()){
                return true;
            }

        }

        return false;
    };

    @Override
    public void saveAnswer(double answer){
        sessionAnswersRepository.save(new SessionAnswersModel(sessionId, msisdn, STEP_NUMBER, answer));
    };

};