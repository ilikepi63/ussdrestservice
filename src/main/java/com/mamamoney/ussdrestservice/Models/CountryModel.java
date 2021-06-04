package com.mamamoney.ussdrestservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "countries")
public class CountryModel {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_code", nullable = false)
    private  String countryCode;

    @Column(name = "country_name", nullable = false)
    private  String countryName; 

    @Column(name = "foreign_currency_code", nullable = false)
    private  String foreignCurrencyCode; 

    public CountryModel(){}

    public CountryModel(String countryCode, String countryName, String foreignCurrencyCode){
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.foreignCurrencyCode = foreignCurrencyCode;
    };

    public Long getId(){
        return this.id;
    }

    public String getCountryName(){
        return this.countryName;  
    };

    public String getForeignCurrencyCode(){
        return foreignCurrencyCode;
    }

}   