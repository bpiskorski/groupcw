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

