package com.napier.sem;

import com.napier.sem.country.Country;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;

public class App {

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to database
        if(args.length < 1){
            a.connect("localhost:33060", 0);
        }else{
            a.connect("db:3306", 30000);
        }


        // Select one of these below (number 6 is already selected):
        // 1) Provide all countries in a world from largest population to smallest.
        //String input = "select * from world.country order by Population desc;";
        // 2) Provide countries in a region from largest population to smallest
        //String input = "select * from world.country Where Region = 'Eastern Asia' order by Population desc;";
        // 3) Provide counties from largest population to smallest in a continent
        //String input = "select * from world.country Where Continent = 'Eastern Asia' order by Population desc;";
        // 4) The top N populated countries in a region where N is provided by the user.
        //String input = "select * from world.country where Region ='Eastern Asia' order by Population desc limit 3;";
        // 5)The top N populated countries in a continent where N is provided by the user.
        //String input = "select  * from world.country where Continent ='Asia' order by Population desc limit 3;";
        // 6)The top N populated countries in the world where N is provided by the user.
        String input = "select * from world.country order by Population desc limit 3;";
        //a.countryrep(input);

        /* // Get all capital cities
        System.out.println("Calling GetCapitalCities (Ordered By Population)...");
        // Extract city information
        ArrayList<Results> results = GetCapitalCities();
        */

        /* // Get capital cities by continent
        System.out.println("Calling GetContinentCities (Ordered By population)...");
        // Extract city information
        ArrayList<Results> results = GetContinentCities("Europe");
        if(results != null){
            a.PrintCityResults(results);
            System.out.println("Number of results: " + results.size());
        }*/

        /* // Get capital cities by region
        System.out.println("Calling GetRegionCities (Ordered By population)...");
        // Extract city information
        ArrayList<Results> results = GetRegionCities(con1, "Northern Europe");
        if(results != null){
            a.PrintCityResults(results);
            System.out.println("Number of results: " + results.size());
        }*/

        /* // Get n capital cities in the world
        System.out.println("Calling Get_N_CapitalCities (Ordered By population)...");
        // Extract city information
        ArrayList<Results> results = Get_N_CapitalCities(5);
        if(results != null){
            a.PrintCityResults(results);
            System.out.println("Number of results: " + results.size());
        }*/

        /*// Get n capital cities by continent
        System.out.println("Calling Get_N_ContinentCities (Ordered By population)...");
        // Extract city information
        ArrayList<Results> results = Get_N_ContinentCities("Europe", 5);
        PrintCityResults(results);
        if(results != null){
            a.PrintCityResults(results);
            System.out.println("Number of results: " + results.size());
        }*/

        // Get n capital cities by region
        System.out.println("Calling Get_N_RegionCities (Ordered By population)...");
        // Extract city information
        ArrayList<Results> results = a.Get_N_RegionCities("Southern Europe", 5);

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connect to the MySQL database.
     * @param conString db:3306 for docker and localhost:33060 for local or Integration Tests
     * @param //i
     */

    // Database Username, Password
    static final String USER = "root";
    static final String PASS = "example";

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
                con = DriverManager.getConnection("jdbc:mysql://" + conString + "/world?allowPublicKeyRetrieval=true&useSSL=false", USER, PASS);
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

    public void citiesReport() {
        StringBuilder sb = new StringBuilder();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String sql = "select * from city order by Population desc;";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            // Return new employee if valid.
            // Check one is returned
            while (rset.next()) {
                String name = rset.getString("name");
                String countryCode = rset.getString("countryCode");
                String district = rset.getString("district");
                Integer population = rset.getInt("population");
                City city = new City(name, countryCode, district, population);
                sb.append(city.toString() + "\r\n");
            }
            new File("./tmp/").mkdir();
            System.out.println("sb" + sb);
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./tmp/report1.txt")));
            writer.write(sb.toString());
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return;
        }
    }

    public void countryrep(String input) {
        System.out.println("Creating the country report...");
        StringBuilder sb  = new StringBuilder();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String sql ="select * from world.country order by Population desc limit 3;";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            // Return  if valid.
            // Check one is returned
            while (rset.next()) {
                String code = rset.getString("code");
                String name = rset.getString("name");
                String continent = rset.getString("continent");
                String region = rset.getString("region");
                Integer population = rset.getInt("population");
                Integer capital = rset.getInt("capital");
                Country country = new Country(code, name, continent, region, population, capital);
                sb.append(country.toString() + "\r\n");
                System.out.println(country.toString());
            } new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/task06.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Report created look in a reports folder!");
            System.out.println(""); // Leave one line empty for clear view
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return;
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
            if(ress != null){
                PrintCityResults(ress);
                System.out.println("Number of results: " + ress.size());
                System.out.println(""); // Leave one line empty for clear view
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
            if(ress != null){
                PrintCityResults(ress);
                System.out.println("Number of results: " + ress.size());
                System.out.println(""); // Leave one line empty for clear view
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
            if(ress != null){
                PrintCityResults(ress);
                System.out.println("Number of results: " + ress.size());
                System.out.println(""); // Leave one line empty for clear view
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
            if(ress != null){
                PrintCityResults(ress);
                System.out.println("Number of results: " + ress.size());
                System.out.println(""); // Leave one line empty for clear view
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
            if(ress != null){
                PrintCityResults(ress);
                System.out.println("Number of results: " + ress.size());
                System.out.println(""); // Leave one line empty for clear view
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
            if(ress != null){
                PrintCityResults(ress);
                System.out.println("Number of results: " + ress.size());
                System.out.println(""); // Leave one line empty for clear view
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