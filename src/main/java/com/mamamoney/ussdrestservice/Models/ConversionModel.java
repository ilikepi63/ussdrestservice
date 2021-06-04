package com.mamamoney.ussdrestservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.IdClass;

import java.math.BigDecimal;

@Entity
@Table(name = "conversion_rates")
@IdClass(ConversionModelId.class)
public class ConversionModel {

    @Id
    @Column(name = "to_currency", nullable = false)
    private  String to; 

    @Id
    @Column(name = "from_currency", nullable = false)
    private  String from;

    @Column(name = "conversion_rate", nullable = false)
    private double conversionRate;

    public ConversionModel(){}

    public ConversionModel(String to, String from, double conversionRate){
        this.to = to;
        this.from = from;
        this.conversionRate = conversionRate;
    };

    public double getConversionRate(){
        return this.conversionRate;
    }

}