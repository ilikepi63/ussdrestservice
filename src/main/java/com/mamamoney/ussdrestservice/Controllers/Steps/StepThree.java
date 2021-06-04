package com.mamamoney.ussdrestservice;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

class StepThree implements Step<Boolean>{

    private final String sessionId;
    private final String msisdn;
    private static int STEP_NUMBER = 3;

    private SessionStepsRepository stepRepository;
    private ConversionRepository conversionRepository;
    private SessionAnswersRepository sessionAnswersRepository;

    StepOne stepOne;
    StepTwo stepTwo;

    StepThree(String sessionId, 
                String msisdn, 
                SessionStepsRepository stepRepository, 
                SessionAnswersRepository sessionAnswersRepository, 
                ConversionRepository conversionRepository , 
                StepOne stepOne, 
                StepTwo stepTwo){
        this.sessionId = sessionId;
        this.msisdn = msisdn;
        this.stepRepository = stepRepository;
        this.conversionRepository = conversionRepository;
        this.sessionAnswersRepository = sessionAnswersRepository;

        this.stepOne = stepOne;
        this.stepTwo = stepTwo;
    };

    static int getStepNumber(){
        return STEP_NUMBER;
    }

    @Override
    public Boolean getAnswer(){
        // not relevant to get this answer
        return null;
    };

    @Override
    public String getQuestion(){

        SessionStepsModel step = stepRepository.findById(STEP_NUMBER).get();

        CountryModel country = stepOne.getAnswer();

        Locale currentLocale = Locale.getDefault();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(','); 

        String amountInForeignCurrency = (new DecimalFormat("#.00", otherSymbols)).format(getAmountInForeignCurrency());

        return  String.format(step.getQuestion(), amountInForeignCurrency, getForeignCurrencyCode());
    };

    @Override
    public boolean validateAnswer(double answer){
        return answer == 1;
    };

    @Override
    public void saveAnswer(double answer){
        sessionAnswersRepository.save(new SessionAnswersModel(sessionId, msisdn, STEP_NUMBER, answer));
    };

    private String getForeignCurrencyCode(){

        CountryModel country = stepOne.getAnswer();

        return country.getForeignCurrencyCode();

    }


    private double getAmountInForeignCurrency(){

        // hardcoding this for the requirements of this, 
        // but I assume that the "fromCountry" aspect of this currency
        // conversion will be derived from either the msisdn or the locale of the actual server(assuming that 
        // certain requests in certain countries or regions get routed to a specific country)
        String fromCountry = "ZAR";
        String toCountry = getForeignCurrencyCode();

        ConversionModel conversion = conversionRepository.findById(new ConversionModelId(toCountry, fromCountry)).get();

        BigDecimal conversionRate = new BigDecimal(conversion.getConversionRate(), MathContext.DECIMAL64);

        BigDecimal amountToConvert = new BigDecimal(stepTwo.getAnswer(), MathContext.DECIMAL64);
        
         conversionRate.multiply(amountToConvert);

        return conversionRate.multiply(amountToConvert).doubleValue();
    }

};