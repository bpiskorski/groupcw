package com.napier.sem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class App
{
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/world_db";
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args)
    {
        System.out.println("Hello World! This is a test message!");

        System.out.println("Connecting to a selected database...");
        // Open a connection
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {
            System.out.println("Connected database successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    }


