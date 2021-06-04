package com.mamamoney.ussdrestservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(SessionStepsRepository sessionStepsRepository, CountryRepository countryRepository, ConversionRepository conversionRepository) {

    return args -> {

        // steps
        log.info("Preloading " + sessionStepsRepository.save(new SessionStepsModel(1, "Welcome to Mama Money!\n Where would you like to send money to?")));
        log.info("Preloading " + sessionStepsRepository.save(new SessionStepsModel(2, "How much money would you like to send to %s?")));
        log.info("Preloading " + sessionStepsRepository.save(new SessionStepsModel(3, "The person you are sending to will receive: %s %s \n1) OK")));
        log.info("Preloading " + sessionStepsRepository.save(new SessionStepsModel(4, "Thank you for using Mama Money!")));

        // countries
        log.info("Preloading " + countryRepository.save(new CountryModel("KE", "Kenya", "KES")));
        log.info("Preloading " + countryRepository.save(new CountryModel("MW", "Malawi", "MWK")));

        //conversions
        log.info("Preloading " + conversionRepository.save(new ConversionModel("MWK", "ZAR", 6.10)));
        log.info("Preloading " + conversionRepository.save(new ConversionModel("KES", "ZAR", 42.50)));
    };
  }
}