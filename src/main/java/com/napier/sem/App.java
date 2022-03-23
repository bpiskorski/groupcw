package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World! City Reports Feature!");

        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        /* // Get all capital cities
        System.out.println("Calling GetCapitalCities (Ordered By Population)...");
        // Extract city information
        ArrayList<Results> results = GetCapitalCities(con1);
        PrintCityResults(results);
        System.out.println("Number of results: " + results.size());*/

        /* // Get capital cities by continent
        System.out.println("Calling GetContinentCities (Ordered By population)...");
        // Extract city information
        ArrayList<Results> results = GetContinentCities(con1, "Europe");
        PrintCityResults(results);
        System.out.println("Number of results: " + results.size());*/

        /* // Get capital cities by region
        System.out.println("Calling GetRegionCities (Ordered By population)...");
        // Extract city information
        ArrayList<Results> results = GetRegionCities(con1, "Northern Europe");
        PrintCityResults(results);
        System.out.println("Number of results: " + results.size());*/

        /* // Get n capital cities in the world
        System.out.println("Calling Get_N_CapitalCities (Ordered By population)...");
        // Extract city information
        ArrayList<Results> results = Get_N_CapitalCities(con1, 5);
        PrintCityResults(results);
        System.out.println("Number of results: " + results.size());*/

        /*// Get n capital cities by continent
        System.out.println("Calling Get_N_ContinentCities (Ordered By population)...");
        // Extract city information
        ArrayList<Results> results = Get_N_ContinentCities(con1, "Europe", 5);
        PrintCityResults(results);
        System.out.println("Number of results: " + results.size());*/

        // Get n capital cities by region
        System.out.println("Calling Get_N_RegionCities (Ordered By population)...");
        // Extract city information
        ArrayList<Results> results = a.Get_N_RegionCities("Southern Europe", 5);
        a.PrintCityResults(results);
        System.out.println("Number of results: " + results.size());

        // Disconnect from database
        a.disconnect();
    }

    // Connection to MySQL database.
    private Connection con = null;

    static final String DB_URL = "jdbc:mysql://db:3306/world";
    static final String USER = "root";
    static final String PASS = "root";

    // Connect to the MySQL database.
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 30;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(2000);
                // Connect to database
                con = DriverManager.getConnection(DB_URL,USER,PASS);
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

    // Disconnect from the MySQL database.
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

    /*GET LIST OF TABLES (NOT FUNCTIONAL)*/
    public void GetTables(){
        System.out.println("Getting tables...");
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "SHOW TABLES";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            //ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next())
            {
                System.out.println(rset);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get tables");
            //return null;
        }
    }

    // Get list of all capital cities by population
    public ArrayList<Results> GetCapitalCities(){
        System.out.println("Getting cities (All)...");
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city INNER JOIN country ON city.ID = country.Capital ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Results> ress = new ArrayList<Results>();
            while (rset.next())
            {
                Results res = new Results();
                res.id = rset.getInt("city.ID");
                res.cityName = rset.getString("city.Name");
                res.population = rset.getInt("city.Population");
                res.countryCode = rset.getString("city.CountryCode");
                res.district = rset.getString("city.District");
                ress.add(res);
            }
            return ress;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    // Get list of capital cities by continent
    public ArrayList<Results> GetContinentCities(String continent){
        System.out.println("Getting cities (Continent)...");
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city INNER JOIN country ON city.ID = country.Capital WHERE country.Continent LIKE '" + continent + "' ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract city information
            ArrayList<Results> ress = new ArrayList<Results>();
            while (rset.next())
            {
                Results res = new Results();
                res.id = rset.getInt("city.ID");
                res.cityName = rset.getString("city.Name");
                res.population = rset.getInt("city.Population");
                res.countryCode = rset.getString("city.CountryCode");
                res.district = rset.getString("city.District");
                ress.add(res);
            }
            return ress;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    // Get list of capital cities by region
    public ArrayList<Results> GetRegionCities(String region){
        System.out.println("Getting cities (Region)...");
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city INNER JOIN country ON city.ID = country.Capital WHERE country.Region LIKE '" + region + "' ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract city information
            ArrayList<Results> ress = new ArrayList<Results>();
            while (rset.next())
            {
                Results res = new Results();
                res.id = rset.getInt("city.ID");
                res.cityName = rset.getString("city.Name");
                res.population = rset.getInt("city.Population");
                res.countryCode = rset.getString("city.CountryCode");
                res.district = rset.getString("city.District");
                ress.add(res);
            }
            return ress;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    // Get list of N capital cities in the world
    public ArrayList<Results> Get_N_CapitalCities(int n){
        System.out.println("Getting " + n + " cities (All)...");
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city INNER JOIN country ON city.ID = country.Capital ORDER BY city.Population DESC LIMIT " + n;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract city information
            ArrayList<Results> ress = new ArrayList<Results>();
            while (rset.next())
            {
                Results res = new Results();
                res.id = rset.getInt("city.ID");
                res.cityName = rset.getString("city.Name");
                res.population = rset.getInt("city.Population");
                res.countryCode = rset.getString("city.CountryCode");
                res.district = rset.getString("city.District");
                ress.add(res);
            }
            return ress;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    // Get list of N capital cities by continent
    public ArrayList<Results> Get_N_ContinentCities(String continent, int n){
        System.out.println("Getting " + n + " cities (Continent)...");
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city INNER JOIN country ON city.ID = country.Capital WHERE country.Continent LIKE '" + continent + "' ORDER BY city.Population DESC LIMIT " + n;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract city information
            ArrayList<Results> ress = new ArrayList<Results>();
            while (rset.next())
            {
                Results res = new Results();
                res.id = rset.getInt("city.ID");
                res.cityName = rset.getString("city.Name");
                res.population = rset.getInt("city.Population");
                res.countryCode = rset.getString("city.CountryCode");
                res.district = rset.getString("city.District");
                ress.add(res);
            }
            return ress;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    // Get list of N capital cities by continent
    public ArrayList<Results> Get_N_RegionCities(String region, int n){
        System.out.println("Getting " + n + " cities (Region)...");
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city INNER JOIN country ON city.ID = country.Capital WHERE country.Region LIKE '" + region + "' ORDER BY city.Population DESC LIMIT " + n;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract city information
            ArrayList<Results> ress = new ArrayList<Results>();
            while (rset.next())
            {
                Results res = new Results();
                res.id = rset.getInt("city.ID");
                res.cityName = rset.getString("city.Name");
                res.population = rset.getInt("city.Population");
                res.countryCode = rset.getString("city.CountryCode");
                res.district = rset.getString("city.District");
                ress.add(res);
            }
            return ress;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public void PrintCityResults(ArrayList<Results> results){
        // Check results is not null
        if (results == null)
        {
            System.out.println("No results");
            return;
        }
        // Print header
        System.out.println(String.format("%-10s %-40s %-10s %-8s %-8s", "City ID", "City Name", "City Population", "City Country Code", "City District"));
        // Loop over all results in the list
        for (Results res : results)
        {
            if (results == null)
                continue;
            String emp_string =
                    String.format("%-10s %-40s %-15s %-18s %-15s",
                            res.id, res.cityName, res.population, res.countryCode, res.district);
            System.out.println(emp_string);
        }
    }

    public void PrintCountryResults(ArrayList<Results> results){
        // Check results is not null
        if (results == null)
        {
            System.out.println("No results");
            return;
        }
        // Print header
        System.out.println(String.format("%-10s %-40s %-10s %-8s %-8s %-10s %-40s %-10s %-8s %-8s %-10s %-10s %-8s %-8s",
                "Country Code", "Country Name", "Continent", "Region", "Surface Area", "Independent Year", "Population",
                "Life Expectancy", "gnp", "gnp (OLD)", "Local Name", "Head of State", "Capital", "Country Code 2"));
        // Loop over all results in the list
        for (Results res : results)
        {
            if (results == null)
                continue;
            String emp_string =
                    String.format("%-10s %-40s %-10s %-8s %-8s %-10s %-40s %-10s %-8s %-8s %-10s %-10s %-8s %-8s",
                            res.countryCode, res.countryName, res.continent, res.region, res.surfaceArea, res.indepYear, res.pop,
                            res.lifeExpectancy, res.gnp, res.gnpOld, res.localName, res.headOfState, res.capital, res.countryCode2);
            System.out.println(emp_string);
        }
    }

    public void PrintLanguageResults(ArrayList<Results> results){
        // Check results is not null
        if (results == null)
        {
            System.out.println("No results");
            return;
        }
        // Print header
        System.out.println(String.format("%-10s %-40s %-10s %-8s",
                "Country Code", "Language", "Is Official", "Percentage"));
        // Loop over all results in the list
        for (Results res : results)
        {
            if (results == null)
                continue;
            String emp_string =
                    String.format("%-10s %-40s %-10s %-8s",
                            res.country_code, res.language, res.isOfficial, res.percentage);
            System.out.println(emp_string);
        }
    }

}