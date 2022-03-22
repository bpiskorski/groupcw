package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CountryAppTest
{
    static CountryApp app;

    @BeforeAll
    static void init()
    {
        app = new CountryApp();
        app.connect("localhost:33060", 0);
    }

    @Test
    void countryrep()
    {
        app.countryrep();
    }

    @AfterAll
    static void close(){
        app.disconnect();
    }
}