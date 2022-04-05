package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App(); // creating an instance of App to work with
        // app.connect("localhost:33060", 0);
    }

    @Test
    void countryrep() {
        app.countryrep("select * from world.country order by Population desc limit 3;");
    }

    @Test
    void CapitalCities() // Get all capital cities
    {
        app.GetCapitalCities();
    }

    @Test
    void ContinentCities() // Get all capital cities on defined continent
    {
        app.GetContinentCities("Europe");
    }

    @Test
    void RegionCities() // Get all capital cities in defined region
    {
        app.GetRegionCities("Southern Europe");
    }

    @Test
    void N_CapitalCities() // Get N capital cities
    {
        app.Get_N_CapitalCities(5);
    }

    @Test
    void N_ContinentCities() // Get N capital cities on defined continent
    {
        app.Get_N_ContinentCities("Europe", 5);
    }

    @Test
    void N_RegionCities() // Get N capital cities in defined region
    {
        app.Get_N_RegionCities("Southern Europe", 5);
    }

    @AfterAll
    static void close() {
        app.disconnect();
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
        app.getNCitiesFromContinent(5, "Europe");
    }
    @Test
    void getNCitiesFromRegion() {
        app.getNCitiesFromRegion(5, "Eastern Europe");
    }
    @Test
    void getNCitiesFromCountry() {
        app.getNCitiesFromCountry(5, "United Kingdom");
    }
    @Test
    void getNCitiesFromDistrict() {
        app.getNCitiesFromDistrict(5, "England");
    }
    @Test
    void getCities() {
        String query = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District" +
                " FROM city" +
                " ORDER BY city.Population DESC";
        app.getCities(query);
    }

}