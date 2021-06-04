package com.mamamoney.ussdrestservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UssdController {

    private final SessionRepository sessionRepository;
    private final SessionStepsRepository sessionStepsRepository;
    private final SessionAnswersRepository sessionAnswersRepository;
    private final CountryRepository countryRepository;
    private final ConversionRepository conversionRepository;

    private static int LAST_STEP_NUMBER = 4; 

    UssdController(SessionRepository sessionRepository, SessionStepsRepository sessionStepsRepository, SessionAnswersRepository sessionAnswersRepository, CountryRepository countryRepository, ConversionRepository conversionRepository) {
      this.sessionRepository = sessionRepository;
      this.sessionStepsRepository = sessionStepsRepository;
      this.sessionAnswersRepository = sessionAnswersRepository;
      this.countryRepository = countryRepository;
      this.conversionRepository = conversionRepository;
    }

    @PostMapping("/ussd")
    public UssdResponse ussd(@RequestBody UssdRequest ussdRequest) {
      
      Optional<SessionModel> resultantSessionModel = sessionRepository.findById(new SessionModelId(ussdRequest.getSessionId(), ussdRequest.getMsisdn()));

      return resultantSessionModel.isPresent() ?  updateSession(ussdRequest,resultantSessionModel.get()) : generateSession(ussdRequest);

	  }

    // new stuff
    private Step getStepFromNumber(int stepNumber, UssdRequest request){
     switch(stepNumber) {
        case 1:
          return new StepOne(request.getSessionId(), request.getMsisdn(), sessionStepsRepository, countryRepository, sessionAnswersRepository);
        case 2:
          return new StepTwo(request.getSessionId(), request.getMsisdn(), sessionStepsRepository, sessionAnswersRepository, (StepOne) getStepFromNumber(StepOne.getStepNumber(), request));
        case 3:

          StepOne stepOne = (StepOne) getStepFromNumber(StepOne.getStepNumber(), request);
          StepTwo stepTwo = (StepTwo) getStepFromNumber(StepTwo.getStepNumber(), request);

          return new StepThree(request.getSessionId(), request.getMsisdn(), sessionStepsRepository, sessionAnswersRepository, conversionRepository, stepOne, stepTwo);
        case 4:
          return new StepFour(request.getSessionId(), request.getMsisdn(), sessionStepsRepository, sessionAnswersRepository);
        default:
          return null;
      }
    }

    // update the session to the next step
    private UssdResponse updateSession(UssdRequest request, SessionModel session){

        // first we need to check the validity of the answer
        double answer = request.getUserEntry();

        if(session.getCurrentStep() == LAST_STEP_NUMBER) return new UssdResponse(request.getSessionId(), getStepFromNumber(StepFour.getStepNumber(), request).getQuestion());

        // validate the answer
        Step currentStep = getStepFromNumber(session.getCurrentStep(), request);

        if(!currentStep.validateAnswer(answer)){

          // we're going to go back to step 1 if the answer given wasn't correct
          // following needs to be done in a transaction
          this.sessionRepository.save(session.setCurrentStep(StepOne.getStepNumber()));

          return new UssdResponse(request.getSessionId(), getStepFromNumber(StepOne.getStepNumber(), request).getQuestion());
        }

        currentStep.saveAnswer(answer);

        int nextStepNumber = session.getCurrentStep() + 1;

        Step nextStep = getStepFromNumber(nextStepNumber, request);

        // following needs to be done in a transaction
        this.sessionRepository.save(session.incrementCurrentStep());

        return new UssdResponse(request.getSessionId(), nextStep.getQuestion());
    }


    // method to generate a new session - session id generated on the client side
    private UssdResponse generateSession(UssdRequest request){

      // we create step one
      Step step = getStepFromNumber(StepOne.getStepNumber(), request);

      // we create a new session
      SessionModel newSession = new SessionModel(request.getSessionId(), request.getMsisdn());

      // update the new session with a step number set to 1
      sessionRepository.save(newSession.setCurrentStep(1));

      // return a response
		  return new UssdResponse(request.getSessionId(), step.getQuestion());

    }
    

}