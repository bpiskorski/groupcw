package com.napier.sem;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ServiceLoader;

public class DbConnect {
    static final String DB_URL = "jdbc:mysql://db:3306/world?useSSL=false";
    static final String USER = "root";
    static final String PASS = "root";

    public DbConnect() {
    }

    public void connect(){
        Connection conn = null;

        System.out.println("Connecting to a selected database...");

      /*     try
            {
                // Load Database driver
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();


            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                System.out.println("Could not load SQL driver");

                 System.exit(-1);
            }
*/
        // Connection to the database
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            //Thread.sleep(30000);
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 100;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                //Thread.sleep(30000);

                // Connect to database
                /*conn = DriverManager.getConnection(DB_URL,USER,PASS);*/
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306?"+
                        "user=root&password=root");
                System.out.println("Successfully connected");
                // Wait a bit
                Thread.sleep(10000);
                // Exit for loop
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }
}
