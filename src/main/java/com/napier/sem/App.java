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



    public static void main(String[] args)
    {

        System.out.println("Hello World! This is a test message!");

       DbConnect con1 = new DbConnect();
        con1.connect();


    }
}


