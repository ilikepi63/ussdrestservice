
package com.mamamoney.ussdrestservice;

public class BadRequestResponse{
    private final int statusCode; 
    private final String message;

    public BadRequestResponse(){
        this.statusCode = 400;
        this.message = "Bad Request";
    };

    public BadRequestResponse(String message){
        this.statusCode = 400;
        this.message = message;
    };
    
}