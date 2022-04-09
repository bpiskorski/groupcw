package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        // Connect to database
        app = new App();
        app.connect("localhost:33060", 0);
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
    static void close(){
        app.disconnect();
    }
}