package com.mamamoney.ussdrestservice;

import java.io.Serializable;

public class ConversionModelId implements Serializable {

    private  String to; 
    private  String from;

    public ConversionModelId(){}

    public ConversionModelId(String to, String from) {
        this.to = to;
        this.from = from;
    }
}