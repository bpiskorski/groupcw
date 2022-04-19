package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest {
    static App app;

    // Connect to db on start
    @BeforeAll
    static void init() {
        app = new App(); // creating an instance of App to work with
        app.connect("localhost:33060", 1000);
    }


    // Capital Cities Test
    @Test
    void worldCapitals() // Get all capital cities
    {
        app.getCapitals_World();
    }
    @Test
    void continentCapitals() // Get all capital cities on defined continent
    {
        app.getCapitals_Continent("Europe");
    }
    @Test
    void regionCapitals() // Get all capital cities in defined region
    {
        app.getCapitals_Region("Southern Europe");
    }
    @Test
    void n_WorldCapitals() // Get N capital cities
    {
        app.getCapitals_World_N(5);
    }
    @Test
    void n_ContinentCapitals() // Get N capital cities on defined continent
    {
        app.getCapitals_Continent_N("Europe", 5);
    }
    @Test
    void n_RegionCapitals() // Get N capital cities in defined region
    {
        app.getCapitals_Region_N("Southern Europe", 5);
    }
    @Test
    void getNoCapitals(){
        // This will return no results (However there is Solitude - the capital city of Skyrim)
        app.getCapitals_Region("Haafingar");
    }


    // Countries Tests
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
    void country_atlantis_continent(){
        // This will return no results
        app.country_continent("Atlantis");
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
    @Test
    void getNoCities(){
        // This will return no results (even though there are multiple cities in that region of Middle-Earth)
        app.getCitiesFromRegion("Gondor");
    }

    // Language tests
    @Test
    void getLanguages(){
        app.getLanguagesBySpeakers();
    }
    @Test
    void getChinese(){app.getLanguageByName("Chinese");}
    @Test
    void getEnglish(){app.getLanguageByName("English");}
    @Test
    void getHindi(){app.getLanguageByName("Hindi");}
    @Test
    void getSpanish(){app.getLanguageByName("Spanish");}
    @Test
    void getArabic(){app.getLanguageByName("Arabic");}

    // Population tests
    @Test
    void populationInContinent(){app.getPopulationInContinent("Europe");}
    @Test
    void populationInRegion(){app.getPopulationInRegion("Southern Europe");}
    @Test
    void populationInCountry(){app.getPopulationInCountry("United Kingdom");}
    @Test
    void populationInWorld(){app.getPopulationInWorld();}

    // Disconnect from db on end
    @AfterAll
    static void close() {
        app.disconnect();
    }

}