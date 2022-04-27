package com.napier.sem;

/**
 * This is the results class containing all required variables for result retrieval
 */
public class Results {
    // City table ('city')
    int id;
    String cityName;
    String countryCode;
    String district;
    int population;

    // Country table ('country')
    String country_Code;
    String countryName;
    String continent;
    String region;
    float surfaceArea;
    int indepYear;
    int pop;
    float lifeExpectancy;
    float gnp;
    float gnpOld;
    String localName;
    String governmentForm;
    String headOfState;
    String capital;
    String countryCode2;

    // Country Language table ('countrylanguage')
    String language;
    int speakers;
    double world_percentage;

    // Population table ('population')
    String name;
    long totalPop;
    long cityPop;
    String nonCityPop;
}
