package com.napier.sem;
/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;*/
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class App{

    static final String DB_URL = "jdbc:mysql://localhost:3306";
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args)
    {
        System.out.println("Hello World! This is a test message!");

        System.out.println("Connecting to a selected database...");
        // Open a connection
        /*try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {
            System.out.println("Connected database successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
       /* Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver d = drivers.nextElement();*/
    try
    {
           // Load Database driver

           Class.forName("com.mysql.cj.jdbc.Driver");
       } catch (ClassNotFoundException e) {
           System.out.println("Could not load SQL driver");
           System.exit(-1);
       }


       // Connection to the database
       Connection con = null;
       int retries = 100;
       for (int i = 0; i < retries; ++i) {
           System.out.println("Connecting to database...");
           try {
               // Wait a bit for db to start
               Thread.sleep(30000);
               // Connect to database
               con = DriverManager.getConnection("jdbc:mysql://world_db:3306/world_db?useSSL=false", "root", "root");
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
   /* private List<String> getDriversData() {
        List<String> drivers = new ArrayList<String>();
        for (Enumeration<Driver> e = DriverManager.getDrivers(); e.hasMoreElements();) {
            Driver driver = e.nextElement();
            drivers.add(driver.getClass().getName());
        }
        return drivers;
    }*/
}



