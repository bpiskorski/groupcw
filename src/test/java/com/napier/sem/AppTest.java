package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App(); // creating an instance of App to work with
        app.connect("localhost:33060", 0);
    }

    // Cities tests
    @Test
    void getAllCities() {
        app.getAllCities();
    }
    @Test
    void getCitiesFromContinent() {
        app.getCitiesFromContinent("Europe");
    }
    @Test
    void getCitiesFromRegion() {
        app.getCitiesFromRegion("Eastern Europe");
    }
    @Test
    void getCitiesFromCountry() {
        app.getCitiesFromCountry("United Kingdom");
    }
    @Test
    void getCitiesFromDistrict() {
        app.getCitiesFromDistrict("Scotland");
    }
    @Test
    void getNCitiesFromWorld() {
        app.getNCitiesFromWorld(5);
    }
    @Test
    void getNCitiesFromContinent() {
        app.getNCitiesFromContinent("Europe", 5);
    }
    @Test
    void getNCitiesFromRegion() {
        app.getNCitiesFromRegion("Eastern Europe", 5);
    }
    @Test
    void getNCitiesFromCountry() {
        app.getNCitiesFromCountry("United Kingdom", 5);
    }
    @Test
    void getNCitiesFromDistrict() {
        app.getNCitiesFromDistrict("England", 5);
    }
    @Test
    void getCities() {
        String query = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District" +
                " FROM city" +
                " ORDER BY city.Population DESC";
        app.getCities(query);
    }

    @AfterAll
    static void close() {
        app.disconnect();
    }

}