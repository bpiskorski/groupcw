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
}