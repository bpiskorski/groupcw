package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    static App app;

    @BeforeAll      // creating an instance of App to work with
    static void init() {
        app = new App();
        app.connect("localhost:33060", 0);
    }

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
}