package com.napier.sem.country;

public class Country {
    public String code;
    public String name;
    public String continent;
    public String region;
    public int population;
    public int capital;

    public Country(String code, String name, String continent, String region, Integer population, Integer capital) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.capital = capital;
    }

    public Country(String code, String name, int population) {
        this.code = code;
        this.name = name;
        this.population = population;
    }

    public Country(String code, String name, Integer population) {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public String toString2() {
        return "Country: " +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", population=" + population;
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", continent='" + continent + '\'' +
                ", region='" + region + '\'' +
                ", population=" + population +
                ", capital=" + capital +
                '}';
    }
}

