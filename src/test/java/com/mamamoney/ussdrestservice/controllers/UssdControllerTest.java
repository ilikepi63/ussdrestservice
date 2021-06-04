package com.mamamoney.ussdrestservice;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UssdControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testRunThroughSession() throws Exception {

        String json = "{\"sessionId\":\"123\",\"msisdn\":\"123\",\"userEntry\":1}";

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"123\",\"message\":\"Welcome to Mama Money!\\n Where would you like to send money to?\\n1) Kenya\\n2) Malawi\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"123\",\"message\":\"How much money would you like to send to Kenya?\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"123\",\"msisdn\":\"123\",\"userEntry\":100}"))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"123\",\"message\":\"The person you are sending to will receive: 4250.00 KES \\n1) OK\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"123\",\"msisdn\":\"123\",\"userEntry\":1}"))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"123\",\"message\":\"Thank you for using Mama Money!\"}"));
    }
 
    @Test
    public void testRunThroughSessionWithCurrency() throws Exception {

        String json = "{\"sessionId\":\"124\",\"msisdn\":\"123\",\"userEntry\":2}";

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"124\",\"message\":\"Welcome to Mama Money!\\n Where would you like to send money to?\\n1) Kenya\\n2) Malawi\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"124\",\"message\":\"How much money would you like to send to Malawi?\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"124\",\"msisdn\":\"123\",\"userEntry\":123.56}"))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"124\",\"message\":\"The person you are sending to will receive: 753.72 MWK \\n1) OK\"}"));


        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"124\",\"msisdn\":\"123\",\"userEntry\":1}"))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"124\",\"message\":\"Thank you for using Mama Money!\"}"));
    }

     
    @Test
    public void testThatItRefreshesTheSessionOnIncorrectAnswer() throws Exception {

        String json = "{\"sessionId\":\"125\",\"msisdn\":\"123\",\"userEntry\":5}";

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"125\",\"message\":\"Welcome to Mama Money!\\n Where would you like to send money to?\\n1) Kenya\\n2) Malawi\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"125\",\"message\":\"Welcome to Mama Money!\\n Where would you like to send money to?\\n1) Kenya\\n2) Malawi\"}"));

    }

    @Test
    public void testThatItRefreshesTheSessionOnIncorrectAnswerOnConfirmation() throws Exception {

        String json = "{\"sessionId\":\"126\",\"msisdn\":\"123\",\"userEntry\":2}";

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"126\",\"message\":\"Welcome to Mama Money!\\n Where would you like to send money to?\\n1) Kenya\\n2) Malawi\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"126\",\"message\":\"How much money would you like to send to Malawi?\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"126\",\"msisdn\":\"123\",\"userEntry\":123.56}"))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"126\",\"message\":\"The person you are sending to will receive: 753.72 MWK \\n1) OK\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"126\",\"msisdn\":\"123\",\"userEntry\":5}"))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"126\",\"message\":\"Welcome to Mama Money!\\n Where would you like to send money to?\\n1) Kenya\\n2) Malawi\"}"));
    }



    @Test
    public void testThatItReturnsCompletionAfterSessionIsCompleted() throws Exception {

        String json = "{\"sessionId\":\"127\",\"msisdn\":\"123\",\"userEntry\":2}";

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"127\",\"message\":\"Welcome to Mama Money!\\n Where would you like to send money to?\\n1) Kenya\\n2) Malawi\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"127\",\"message\":\"How much money would you like to send to Malawi?\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"127\",\"msisdn\":\"123\",\"userEntry\":123.56}"))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"127\",\"message\":\"The person you are sending to will receive: 753.72 MWK \\n1) OK\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"127\",\"msisdn\":\"123\",\"userEntry\":1}"))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"127\",\"message\":\"Thank you for using Mama Money!\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"127\",\"msisdn\":\"123\",\"userEntry\":1}"))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"127\",\"message\":\"Thank you for using Mama Money!\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"127\",\"msisdn\":\"123\",\"userEntry\":1}"))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"127\",\"message\":\"Thank you for using Mama Money!\"}"));
    }

        @Test
    public void testThatExtraDecimalsDoNotMutateCurrencyOutcome() throws Exception {

        String json = "{\"sessionId\":\"127\",\"msisdn\":\"123\",\"userEntry\":2}";

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"127\",\"message\":\"Welcome to Mama Money!\\n Where would you like to send money to?\\n1) Kenya\\n2) Malawi\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"127\",\"message\":\"How much money would you like to send to Malawi?\"}"));

        this.mockMvc
            .perform(post("/ussd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sessionId\":\"127\",\"msisdn\":\"123\",\"userEntry\":123.568398}"))
            .andExpect(status()
                .is2xxSuccessful())
            .andExpect(content().string("{\"sessionId\":\"127\",\"message\":\"The person you are sending to will receive: 753.72 MWK \\n1) OK\"}"));

    }

}