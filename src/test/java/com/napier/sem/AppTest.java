package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App(); // creating an instance of App to work with
        app.connect("localhost:33060", 1000);
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
    @Test
    void getCity() {
        app.getCityArray("Warszawa");
    }

    @Test
    void country_world(){
        // 1) Provide all countries in a world from largest population to smallest.
        app.country_world();
    }

    @Test
    void country_continent(){
        // 2) Provide counties from largest population to smallest in a continent
        app.country_continent("Asia");
    }

    @Test
    void country_region(){
        // 3) Provide countries in a region from largest population to smallest
        app.country_region("Eastern Asia");
    }

    @Test
    void country_world_N(){
        // 4) Provide the top N populated countries in the world where N is provided by the user.
        app.country_world_N(5);
    }

    @Test
    void country_continent_N(){
        // 5) Provide the top N populated countries in a continent where N is provided by the user.
        app.country_continent_N("Asia", 5);
    }

    @Test
    void country_region_N(){
        // 6) The top N populated countries in a region where N is provided by the user.
        app.country_region_N("Eastern Asia", 5);
    }

    @AfterAll
    static void close() {
        app.disconnect();
    }

}