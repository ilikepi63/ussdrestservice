package com.mamamoney.ussdrestservice;

class StepFour implements Step{

    private final String sessionId;
    private final String msisdn;

    SessionAnswersRepository sessionAnswersRepository;
    SessionStepsRepository stepRepository;

    private static int STEP_NUMBER = 4;

    StepFour(String sessionId, String msisdn, SessionStepsRepository stepRepository,SessionAnswersRepository sessionAnswersRepository){
        this.sessionId = sessionId;
        this.msisdn = msisdn;
        this.sessionAnswersRepository = sessionAnswersRepository;
        this.stepRepository = stepRepository;
    };

    static int getStepNumber(){
        return STEP_NUMBER;
    }

    @Override
    public String getQuestion(){
        SessionStepsModel step = stepRepository.findById(STEP_NUMBER).get();
        return step.getQuestion();
    };

    @Override
    public boolean validateAnswer(double answer) {
        return true;
    }

    @Override
    public Object getAnswer() {
        return null;
    }
    
    @Override
    public void saveAnswer(double answer){
        sessionAnswersRepository.save(new SessionAnswersModel(sessionId, msisdn, STEP_NUMBER, answer));
    };

};