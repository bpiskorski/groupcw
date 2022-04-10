package com.napier.sem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class App {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect("db:3306", 3000);

        // 1) Provide all countries in a world from largest population to smallest.
        //"select * from world.country order by Population desc;";
        a.country_world();

        // 2) Provide counties from largest population to smallest in a continent
        //"select * from world.country Where Continent = 'Eastern Asia' order by Population desc;";
        a.country_continent("Eastern Asia");

        // 3) Provide countries in a region from largest population to smallest
        //"select * from world.country Where Region = 'Eastern Asia' order by Population desc;";
        a.country_region("Eastern Asia");

        // 4)The top N populated countries in the world where N is provided by the user.
        //"select * from world.country order by Population desc limit 3;";
        a.country_world_N(5);

        // 5)The top N populated countries in a continent where N is provided by the user.
        //"select  * from world.country where Continent ='Asia' order by Population desc limit 3;";
        a.country_continent_N("Asia", 5);

        // 6) The top N populated countries in a region where N is provided by the user.
        //"select * from world.country where Region ='Eastern Asia' order by Population desc limit 3;";
        a.country_region_N("Eastern Asia", 5);



        // Extract city information
        //ArrayList<Results> results = a.Get_N_RegionCities("Southern Europe", 5);


        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connect to the MySQL database.
     * @param conString db:3306 for docker and localhost:33060 for local or Integration Tests
     * @param i
     */
    public void connect(String conString, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                //Added allowPublicKeyRetrieval=true to get Integration Tests to work. Possibly due to accessing from another class?
                con = DriverManager.getConnection("jdbc:mysql://" + conString + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }
    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    // 1) Provide all countries in a world from largest population to smallest.
    public ArrayList<Results> country_world() {
        System.out.println("Creating the report (Countries in world by population)...");
        StringBuilder sb  = new StringBuilder();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement - REMOVED
            String sql = "select * from world.country order by Population desc";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            // Extract country information
            ArrayList<Results> ress = new ArrayList<Results>();
            // Return  if valid.
            // Check one is returned
            while (rset.next()) {
                Results res = new Results();
                res.countryCode = rset.getString("code");
                res.countryName = rset.getString("name");
                res.continent = rset.getString("continent");
                res.region = rset.getString("region");
                res.pop = rset.getInt("population");
                res.capital = rset.getInt("Capital");
                ress.add(res);
            }
            // Print to text file
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/task06.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Report created look in a reports folder!");
            // Print reports out
            PrintCountryResults(ress);
            return ress;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    // 2) Provide countries in a continent from largest population to smallest
    public ArrayList<Results> country_continent(String continent) {
        System.out.println("Creating the report (Countries in continent by population)...");
        StringBuilder sb  = new StringBuilder();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement - REMOVED
            String sql = "select * from world.country Where Continent = '" + continent + "' order by Population desc;";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            // Extract country information
            ArrayList<Results> ress = new ArrayList<Results>();
            // Return  if valid.
            // Check one is returned
            while (rset.next()) {
                Results res = new Results();
                res.countryCode = rset.getString("code");
                res.countryName = rset.getString("name");
                res.continent = rset.getString("continent");
                res.region = rset.getString("region");
                res.pop = rset.getInt("population");
                res.capital = rset.getInt("Capital");
                ress.add(res);
            }
            // Print to text file
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/task06.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Report created look in a reports folder!");
            // Print reports out
            PrintCountryResults(ress);
            return ress;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    // 3) Provide countries in a region from largest population to smallest
    public ArrayList<Results> country_region(String region) {
        System.out.println("Creating the report (Countries in region by population)...");
        StringBuilder sb  = new StringBuilder();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement - REMOVED
            String sql = "select * from world.country Where Region = '" + region + "' order by Population desc;";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            // Extract country information
            ArrayList<Results> ress = new ArrayList<Results>();
            // Return  if valid.
            // Check one is returned
            while (rset.next()) {
                Results res = new Results();
                res.countryCode = rset.getString("code");
                res.countryName = rset.getString("name");
                res.continent = rset.getString("continent");
                res.region = rset.getString("region");
                res.pop = rset.getInt("population");
                res.capital = rset.getInt("Capital");
                ress.add(res);
            }
            // Print to text file
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/task06.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Report created look in a reports folder!");
            // Print reports out
            PrintCountryResults(ress);
            return ress;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    // 4)The top N populated countries in the world where N is provided by the user.
    public ArrayList<Results> country_world_N(int n) {
        System.out.println("Creating the report (N Countries in world by population)...");
        StringBuilder sb  = new StringBuilder();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement - REMOVED
            String sql = "select * from world.country order by Population desc limit " + n +";";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            // Extract country information
            ArrayList<Results> ress = new ArrayList<Results>();
            // Return  if valid.
            // Check one is returned
            while (rset.next()) {
                Results res = new Results();
                res.countryCode = rset.getString("code");
                res.countryName = rset.getString("name");
                res.continent = rset.getString("continent");
                res.region = rset.getString("region");
                res.pop = rset.getInt("population");
                res.capital = rset.getInt("Capital");
                ress.add(res);
            }
            // Print to text file
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/task06.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Report created look in a reports folder!");
            // Print reports out
            PrintCountryResults(ress);
            return ress;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    // 5)The top N populated countries in a continent where N is provided by the user.
    public ArrayList<Results> country_continent_N(String continent, int n) {
        System.out.println("Creating the report (N Countries in continent by population)...");
        StringBuilder sb  = new StringBuilder();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement - REMOVED
            String sql = "select  * from world.country where Continent = '" + continent + "' order by Population desc limit " + n +";";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            // Extract country information
            ArrayList<Results> ress = new ArrayList<Results>();
            // Return  if valid.
            // Check one is returned
            while (rset.next()) {
                Results res = new Results();
                res.countryCode = rset.getString("code");
                res.countryName = rset.getString("name");
                res.continent = rset.getString("continent");
                res.region = rset.getString("region");
                res.pop = rset.getInt("population");
                res.capital = rset.getInt("Capital");
                ress.add(res);
            }
            // Print to text file
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/task06.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Report created look in a reports folder!");
            // Print reports out
            PrintCountryResults(ress);
            return ress;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    // 6) The top N populated countries in a region where N is provided by the user.
    public ArrayList<Results> country_region_N(String region, int n) {
        System.out.println("Creating the report (N Countries in region by population)...");
        StringBuilder sb  = new StringBuilder();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement - REMOVED
            String sql = "select * from world.country where Region = '" + region + "' order by Population desc limit " + n +";";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            // Extract country information
            ArrayList<Results> ress = new ArrayList<Results>();
            // Return  if valid.
            // Check one is returned
            while (rset.next()) {
                Results res = new Results();
                res.countryCode = rset.getString("code");
                res.countryName = rset.getString("name");
                res.continent = rset.getString("continent");
                res.region = rset.getString("region");
                res.pop = rset.getInt("population");
                res.capital = rset.getInt("Capital");
                ress.add(res);
            }
            // Print to text file
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/task06.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Report created look in a reports folder!");
            // Print reports out
            PrintCountryResults(ress);
            return ress;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    // Method to print country results
    public void PrintCountryResults(ArrayList<Results> results){
        // Check results is not null
        if (results == null)
        {
            System.out.println("No results");
            return;
        }
        // Print header
        System.out.println(String.format("%-15s %-40s %-15s %-30s %-12s %-12s",
                "Country Code", "Country Name", "Continent", "Region", "Population", "Capital"));
        // Loop over all results in the list
        for (Results res : results)
        {
            if (results == null)
                continue;
            String emp_string =
                    String.format("%-15s %-40s %-15s %-30s %-12s %-12s",
                            res.countryCode, res.countryName, res.continent, res.region, res.pop, res.capital);
            System.out.println(emp_string);
        }
    }
}
