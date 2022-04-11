package com.napier.sem;

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
        if (args.length < 1) {
            a.connect("localhost:33060", 0);
        }
        else {
            a.connect("db:3306", 30000);
        }

        // City reports
        // 1) All the cities in the world organised by largest population to smallest.
        a.getAllCities();
        // 2) All the cities in a continent organised by largest population to smallest.
        a.getCitiesFromContinent("Europe");
        // 3) All the cities in a region organised by largest population to smallest.
        a.getCitiesFromRegion("Southern Europe");
        // 4) All the cities in a country organised by largest population to smallest.
        a.getCitiesFromCountry("United Kingdom");
        // 5) All the cities in a district organised by largest population to smallest.
        a.getCitiesFromDistrict("Scotland");
        // 6) The top N populated cities in the world where N is provided by the user.
        a.getNCitiesFromWorld(5);
        // 7) The top N populated cities in a continent where N is provided by the user.
        a.getNCitiesFromContinent("Europe",5);
        // 8) The top N populated cities in a region where N is provided by the user.
        a.getNCitiesFromRegion("Southern Europe", 5);
        // 9) The top N populated cities in a country where N is provided by the user.
        a.getNCitiesFromCountry("United Kingdom", 5);
        // 10) The top N populated cities in a district where N is provided by the user.
        a.getNCitiesFromDistrict("Scotland", 5);

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connect to the MySQL database.
     *
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

    // City reports

    /**
     *  1) All the cities in the world organised by largest population to smallest from query string allCities
     * @return getCities(allCities)
     */
    public ArrayList<Results> getAllCities() {
        String allCities = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city ORDER BY city.Population DESC";
        System.out.println("Cities in the world:");
        return getCities(allCities);
    }

    /**
     *  2) All the cities in a continent organised by largest population to smallest.
     * @param continent is used in SQL query of world db.
     * @return getCities(query)
     */
    public ArrayList<Results> getCitiesFromContinent(String continent) {
        String citiesFromContinent = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District" +
                "    FROM city" +
                "    INNER JOIN country ON city.CountryCode = country.Code " +
                "    WHERE country.Continent LIKE '" +
                continent + "' ORDER BY city.Population DESC";
        System.out.println("Cities in " + continent + ":");
        return getCities(citiesFromContinent);
    }

    /**
     * 3) All the cities in a region organised by largest population to smallest.
     * @param region is used in SQL query of world db.
     * @return getCities(query)
     */
    public ArrayList<Results> getCitiesFromRegion(String region) {
        String citiesFromRegion = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District " +
                "FROM city" +
                " INNER JOIN country ON city.ID = country.Capital " +
                "WHERE country.Region LIKE '" +
                region + "' ORDER BY city.Population DESC";
        System.out.println("Cities in " + region + ":");
        return getCities(citiesFromRegion);
    }

    /**
     * 4) All the cities in a country organised by largest population to smallest.
     * @param country is used in SQL query of world db.
     * @return getCities(query)
     */
    public ArrayList<Results> getCitiesFromCountry(String country) {
        String citiesFromCountry = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District " +
                "FROM city" +
                "    INNER JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name LIKE '" +
                country + "' ORDER BY city.Population DESC";
        System.out.println("Cities in " + country + ":");
        return getCities(citiesFromCountry);

    }

    /**
     *  5) All the cities in a district organised by largest population to smallest.
     * @param district is used in SQL query of world db.
     * @return getCities(query)
     */
    public ArrayList<Results> getCitiesFromDistrict(String district) {
        String citiesFromDistrict = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District " +
                "FROM city" +
                "    INNER JOIN country ON city.CountryCode = country.Code " +
                "WHERE district LIKE '" +
                district + "' ORDER BY city.Population DESC";
        System.out.println("Cities in " + district + ":");
        return getCities(citiesFromDistrict);
    }

    /**
     * 6) The top N populated cities in the world where N is provided by the user.
     * @param N is used in SQL query of world db.
     * @return getCities(query)
     */
    public ArrayList<Results> getNCitiesFromWorld(int N) {
        String cities = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District" +
                " FROM city" +
                " ORDER BY city.Population DESC LIMIT " + N;
        System.out.println("Top " + N + " cities in the world (by population):");
        return getCities(cities);
    }

    /**
     *  7) The top N populated cities in a continent where N is provided by the user.
     * @param N  is used in SQL query of world db.
     * @param continent is used in SQL query of world db.
     * @return getCities(query)
     */
    public ArrayList<Results> getNCitiesFromContinent(String continent, int N) {
        String citiesFromContinent = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District" +
                "    FROM city" +
                "    INNER JOIN country ON city.CountryCode = country.Code " +
                "    WHERE country.Continent LIKE '" +
                continent + "' ORDER BY city.Population DESC LIMIT " + N;
        System.out.println("Top " + N + " cities in " + continent + " (by population):");
        return getCities(citiesFromContinent);
    }

    /**
     *  8) The top N populated cities in a region where N is provided by the user.
     * @param N  is used in SQL query of world db.
     * @param region is used in SQL query of world db.
     * @return getCities(query)
     */
    public ArrayList<Results> getNCitiesFromRegion(String region, int N) {
        String citiesFromRegion = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District " +
                "FROM city" +
                " INNER JOIN country ON city.ID = country.Capital " +
                "WHERE country.Region LIKE '" +
                region + "' ORDER BY city.Population  DESC LIMIT " + N;
        System.out.println("Top " + N + " cities in " + region + " (by population):");
        return getCities(citiesFromRegion);
    }

    /**
     * 9) The top N populated cities in a country where N is provided by the user.
     * @param N is used in SQL query of world db.
     * @param country is used in SQL query of world db.
     * @return getCities(query)
     */
    public ArrayList<Results> getNCitiesFromCountry(String country, int N) {
        String citiesFromCountry = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District " +
                "FROM city" +
                "    INNER JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name LIKE '" +
                country + "' ORDER BY city.Population DESC LIMIT " + N;
        System.out.println("Top " + N + " cities in " + country + " (by population):");
        return getCities(citiesFromCountry);
    }

    /**
     * 10) The top N populated cities in a district where N is provided by the user.
     * @param N is used in SQL query of world db.
     * @param district is used in SQL query of world db.
     * @return getCities(query)
     */
    public ArrayList<Results> getNCitiesFromDistrict(String district, int N) {
        String citiesFromDistrict = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District " +
                "FROM city" +
                "    INNER JOIN country ON city.CountryCode = country.Code " +
                "WHERE district LIKE '" +
                district + "' ORDER BY city.Population DESC LIMIT " + N;
        System.out.println("Top " + N + " cities in " + district + " (by population):");
        return getCities(citiesFromDistrict);

    }



    /**
     *  Return and print list of cities from a query in world db.
     *  It calls getCityList(rset) formating query results
     * @param query
     * @return ArrayList<Results> of cities data or null
     * @throws Exception Failed to get city details"
     */
    public ArrayList<Results> getCities(String query) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = query;

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Results> ress = getCityList(rset);
            if (ress != null) {
                PrintCityResults(ress);
                System.out.println("Number of results: " + ress.size());
                System.out.println(""); // Leave one line empty for clear view
            }
            return ress;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     *  Get ArrayList of city names, population, country code and location district
     * @param resultSet from World DB SQL query
     * @return ArrayList<Results> cityLists
     * @throws SQLException
     */
    public ArrayList<Results> getCityList(ResultSet resultSet) throws SQLException {
        ArrayList<Results> cityList = new ArrayList<Results>();
        while (resultSet.next()) {
            Results result = new Results();
            result.cityName = resultSet.getString("city.Name");
            result.population = resultSet.getInt("city.Population");
            result.countryCode = resultSet.getString("city.CountryCode");
            result.district = resultSet.getString("city.District");
            cityList.add(result);
        }
        return cityList;
    }

    // Printing methods

    /**
     * Print city data: name, country code, district, population (000s)
     *  or "No results"
     * @param results - world db query
     */
    public void PrintCityResults(ArrayList<Results> results) {
        // Check results is not null
        if (results == null) {
            System.out.println("No results");
            return;
        }
        // Print header
        System.out.println(String.format("%-30s %-15s %-30s %-5s",
                "Name", "Country Code", "District", "Population (000s)"));
        // Loop over all results in the list
        for (Results res : results) {
            if (results == null)
                continue;
            String emp_string =
                    String.format("%-30s %-15s %-30s %-15s",
                            res.cityName, res.countryCode, res.district, res.population/1000);
            System.out.println(emp_string);
        }
    }
}