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
    void WorldCapitals() // Get all capital cities
    {
        app.GetCapitals_World();
    }
    @Test
    void ContinentCapitals() // Get all capital cities on defined continent
    {
        app.GetCapitals_Continent("Europe");
    }
    @Test
    void RegionCapitals() // Get all capital cities in defined region
    {
        app.GetCapitals_Region("Southern Europe");
    }
    @Test
    void N_WorldCapitals() // Get N capital cities
    {
        app.GetCapitals_World_N(5);
    }
    @Test
    void N_ContinentCapitals() // Get N capital cities on defined continent
    {
        app.GetCapitals_Continent_N("Europe", 5);
    }
    @Test
    void N_RegionCapitals() // Get N capital cities in defined region
    {
        app.GetCapitals_Region_N("Southern Europe", 5);
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

    // Disconnect from db on end
    @AfterAll
    static void close() {
        app.disconnect();
    }

}