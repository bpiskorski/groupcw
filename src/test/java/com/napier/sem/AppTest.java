package com.napier.sem;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

public class AppTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App(); // creating an instance of App to work with
        app.connect("localhost:33060", 0);
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
    void GetAllCities()
    {
        app.GetAllCities();
    }

}