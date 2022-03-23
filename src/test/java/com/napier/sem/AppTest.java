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
        app = new App();
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