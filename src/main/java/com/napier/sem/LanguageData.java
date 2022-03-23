package com.napier.sem;

public class LanguageData {


    private String countryCode;
    private String language;
    private String isOfficial;
    private float percentage;


    public LanguageData(String countryCode, String language, String isOfficial, float percentage) {
        this.countryCode = countryCode;
        this.language = language;
        this.isOfficial = isOfficial;
        this.percentage = percentage;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "country code = " + countryCode +
                ", language = '" + language + '\'' +
                ", isOfficial = " + isOfficial +
                ", percentage = " + percentage;
    }
}
