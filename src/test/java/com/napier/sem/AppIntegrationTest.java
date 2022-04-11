package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 1000);

    }

    @Test
    void testGetCity()
    {

//        Employee emp = app.getEmployee(255530);
        Results result = app.getCity("Warszawa");
//       Results city = app. getCityArray("Warszawa");
        assertEquals(result.countryCode, "POL");
        assertEquals(result.district, "Mazowieckie");
    }
}