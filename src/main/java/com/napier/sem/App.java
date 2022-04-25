package com.napier.sem;

import java.io.*;
import java.util.ArrayList;
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
            a.connect("localhost:33060", 1000);
        }
        else {
            a.connect("db:3306", 30000);
        }

        /**
         * Capital City Reports
         */
        // Get all capital cities
        System.out.println("Calling GetCapitals_World (Ordered By Population)...");
        // Extract city information
        a.getCapitals_World();

        // Get capital cities by continent
        System.out.println("Calling GetCapitals_Continent (Ordered By population)...");
        // Extract city information
        a.getCapitals_Continent("Europe");

        // Get capital cities by region
        System.out.println("Calling GetCapitals_Region (Ordered By population)...");
        // Extract city information
        a.getCapitals_Region("Northern Europe");

        // Get n capital cities in the world
        System.out.println("Calling GetCapitals_World_N (Ordered By population)...");
        // Extract city information
        a.getCapitals_World_N(5);

        // Get n capital cities by continent
        System.out.println("Calling GetCapitals_Continent_N (Ordered By population)...");
        // Extract city information
        a.getCapitals_Continent_N("Europe", 5);

        // Get n capital cities by region
        System.out.println("Calling GetCapitals_Region_N (Ordered By population)...");
        // Extract city information
        a.getCapitals_Region_N("Southern Europe", 5);

        /**
         * Country Reports
         */
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

        /**
         * City reports
         */
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

        /**
         Language reports
         */
        // Combined report for required languages organised by the number of speakers
        a.getLanguagesBySpeakers();
        // Individual report for Chinese
        a.getLanguageByName("Chinese");
        // Individual report for English
        a.getLanguageByName("English");
        // Individual report for Hindi
        a.getLanguageByName("Hindi");
        // Individual report for Spanish
        a.getLanguageByName("Spanish");
        // Individual report for Arabic
        a.getLanguageByName("Arabic");

        /**
         * Population Reports
         */
        // The population of people, people living in cities, and people not living in cities in each continent.
        a.getPopulationInContinent("Europe");
        // The population of people, people living in cities, and people not living in cities in each region.
        a.getPopulationInRegion();
        // The population of people, people living in cities, and people not living in cities in each country.
        a.getPopulationInCountry();
        // The population of districts.
        a.getPopulationInDistrict();
        // The population of the world.
        a.getPopulationInWorld();

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

    // Connect to the MySQL database.
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

    // Country reports
    /**
     * 1) Provide all countries in a world from largest population to smallest.
     */
    public ArrayList<Results> country_world() {
        System.out.println("Creating the report (Countries in world by population)...");
        // Create string for SQL statement
        String sql = "select * from world.country order by Population desc;";
        return getCountries(sql, "1_Country_World.md");
    }

    /**
     * 2) Provide countries in a continent from largest population to smallest
     */
    public ArrayList<Results> country_continent(String continent) {
        System.out.println("Creating the report (Countries in continent by population)...");
        // Create string for SQL statement
        String sql = "select * from world.country Where Continent = '" + continent + "' order by Population desc;";
        return getCountries(sql, "2_Country_Continent_" + continent + ".md");
    }

    /**
     * 3) Provide countries in a region from largest population to smallest
     */
    public ArrayList<Results> country_region(String region) {
        System.out.println("Creating the report (Countries in region by population)...");
        // Create string for SQL statement
        String sql = "select * from world.country Where Region = '" + region + "' order by Population desc;";
        return getCountries(sql, "3_Country_Region_" + region + ".md");
    }

    /**
     * 4)The top N populated countries in the world where N is provided by the user.
     */
    public ArrayList<Results> country_world_N(int n) {
        System.out.println("Creating the report (N Countries in world by population)...");
        // Create string for SQL statement
        String sql = "select * from world.country order by Population desc limit " + n +";";
        return getCountries(sql, "4_Country_World_N.md");
    }

    /**
     * 5)The top N populated countries in a continent where N is provided by the user.
     */
    public ArrayList<Results> country_continent_N(String continent, int n) {
        System.out.println("Creating the report (N Countries in continent by population)...");
        // Create string for SQL statement
        String sql = "select  * from world.country where Continent = '" + continent + "' order by Population desc limit " + n +";";
        return getCountries(sql, "5_Country_Continent_" + continent + "_N.md");
    }

    /**
     * 6) The top N populated countries in a region where N is provided by the user.
     */
    public ArrayList<Results> country_region_N(String region, int n) {
        System.out.println("Creating the report (N Countries in region by population)...");
        // Create string for SQL statement
        String sql = "select * from world.country where Region = '" + region + "' order by Population desc limit " + n +";";
        return getCountries(sql, "6_Country_Region_" + region + "_N.md");

    }


    // Capital reports
    /**
     * 1) Get list of all capital cities by population
     */
    public ArrayList<Results> getCapitals_World(){
        System.out.println("Getting capital cities (All)...");
        // Create string for SQL statement
        String strSelect =
                "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city INNER JOIN country ON city.ID = country.Capital ORDER BY city.Population DESC";
        return getCapitals(strSelect, "1_Capitals_World.md");
    }

    /**
     * 2) Get list of capital cities by continent
     */
    public ArrayList<Results> getCapitals_Continent(String continent){
        System.out.println("Getting capital cities (Continent)...");
        // Create string for SQL statement
        String strSelect =
                "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city INNER JOIN country ON city.ID = country.Capital WHERE country.Continent LIKE '" + continent + "' ORDER BY city.Population DESC";
        return getCapitals(strSelect, "2_Capitals_Continent_" + continent + ".md");
    }

    /**
     * 3) Get list of capital cities by region
     */
    public ArrayList<Results> getCapitals_Region(String region){
        System.out.println("Getting capital cities (Region)...");
        // Create string for SQL statement
        String strSelect =
                "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city INNER JOIN country ON city.ID = country.Capital WHERE country.Region LIKE '" + region + "' ORDER BY city.Population DESC";
        return getCapitals(strSelect, "3_Capitals_Region_" + region + ".md");
    }

    /**
     * 4) Get list of N capital cities in the world
     */
    public ArrayList<Results> getCapitals_World_N(int n){
        System.out.println("Getting " + n + " capital cities (All)...");
        // Create string for SQL statement
        String strSelect =
                "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city INNER JOIN country ON city.ID = country.Capital ORDER BY city.Population DESC LIMIT " + n;
        return getCapitals(strSelect, "4_Capitals_World_N.md");
    }

    /**
     * 5) Get list of N capital cities by continent
     */
    public ArrayList<Results> getCapitals_Continent_N(String continent, int n){
        System.out.println("Getting " + n + " capital cities (Continent)...");
        // Create string for SQL statement
        String strSelect =
                "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city INNER JOIN country ON city.ID = country.Capital WHERE country.Continent LIKE '" + continent + "' ORDER BY city.Population DESC LIMIT " + n;
        return getCapitals(strSelect, "5_Capitals_Continent_" + continent + "_N.md");
    }

    /**
     * 6) Get list of N capital cities by continent
     */
    public ArrayList<Results> getCapitals_Region_N(String region, int n){
        System.out.println("Getting " + n + " capital cities (Region)...");
        // Create string for SQL statement
        String strSelect =
                "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city INNER JOIN country ON city.ID = country.Capital WHERE country.Region LIKE '" + region + "' ORDER BY city.Population DESC LIMIT " + n;
        return getCapitals(strSelect, "6_Capitals_Region_" + region + "_N.md");
    }


    // City reports
    /**
     *  1) All the cities in the world organised by largest population to smallest from query string allCities
     * @return getCities(allCities)
     */
    public ArrayList<Results> getAllCities() {
        String allCities = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District FROM city ORDER BY city.Population DESC";
        System.out.println("Cities in the world:");
        return getCities(allCities, "1_Cities_World.md");
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
        return getCities(citiesFromContinent, "2_Cities_Continent_" + continent + ".md");
    }

    /**
     * 3) All the cities in a region organised by largest population to smallest.
     * @param region is used in SQL query of world db.
     * @return getCities(query)
     */
    public ArrayList<Results> getCitiesFromRegion(String region) {
        String citiesFromRegion = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District " +
                "FROM city" +
                " INNER JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region LIKE '" +
                region + "' ORDER BY city.Population DESC";
        System.out.println("Cities in " + region + ":");
        return getCities(citiesFromRegion, "3_Cities_Region_" + region + ".md");
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
        return getCities(citiesFromCountry, "4_Cities_Country_" + country + ".md");

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
        return getCities(citiesFromDistrict, "5_Cities_District_" + district + ".md");
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
        return getCities(cities, "6_Cities_World_N.md");
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
        return getCities(citiesFromContinent, "7_Cities_Continent_" + continent + "_N.md");
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
                " INNER JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region LIKE '" +
                region + "' ORDER BY city.Population  DESC LIMIT " + N;
        System.out.println("The biggest " + N + " cities in " + region);
        return getCities(citiesFromRegion, "8_Cities_Region_" + region + "_N.md");
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
        return getCities(citiesFromCountry, "9_Cities_Country_" + country + "_N.md");
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
        return getCities(citiesFromDistrict, "10_Cities_District_" + district + "_N.md");

    }


    // Language reports
    /**
     * Provide the number of people who speak the following languages from greatest number to smallest, including the percentage
     * of the world population: Chinese, English, Hindi, Spanish, Arabic
     * @return getLanguages(query)
     */
    public ArrayList<Results> getLanguagesBySpeakers(){
        String languages = "SELECT Language, SUM(country.Population*(Percentage/100)) AS Speakers, " +
                "SUM(country.Population*(Percentage/100))/(SELECT SUM(Population) FROM country)*100 AS 'World_%'" +
                "FROM countrylanguage JOIN country ON (country.Code = countrylanguage.CountryCode) " +
                "WHERE Language LIKE 'Chinese' OR Language LIKE 'English' OR Language LIKE 'Hindi' OR Language LIKE 'Spanish' OR Language LIKE 'Arabic' GROUP BY Language ORDER BY Speakers DESC;";
        System.out.println("Getting language results:");
        return getLanguages(languages, "Language_By_Speakers.md");
    }

    /**
     * Provide the number of people who speak the defined language, including the percentage of the world population
     * @return getLanguages(query)
     */
    public ArrayList<Results> getLanguageByName(String languageName){
        String languages = "SELECT Language, SUM(country.Population*(Percentage/100)) AS Speakers, " +
                "SUM(country.Population*(Percentage/100))/(SELECT SUM(Population) FROM country)*100 AS 'World_%'" +
                "FROM countrylanguage JOIN country ON (country.Code = countrylanguage.CountryCode) " +
                "WHERE Language LIKE '" + languageName + "' GROUP BY Language;";
        System.out.println("Getting language results (by language name):");
        return getLanguages(languages, "Language_" + languageName + ".md");
    }


    // Population reports
    /**
     * The population of people, people living in cities, and people not living in cities in each continent.
     * @return getPopulation(query)
     */
    public ArrayList<Results> getPopulationInContinent(String continent){
        String query = "SELECT country.Continent AS 'name', SUM(country.Population) AS 'total_pop', " +
                "(SELECT SUM(city.Population) FROM city JOIN country ON (country.Code = city.CountryCode) WHERE country.Continent LIKE '" + continent +"') AS 'city_pop', " +
                "(SUM(country.Population) - (SELECT SUM(city.Population) FROM city JOIN country ON (country.Code = city.CountryCode) WHERE country.Continent LIKE '" + continent +"')) AS nonCity_pop " +
                "FROM country " +
                "WHERE country.Continent LIKE '" + continent + "' GROUP BY country.Continent;";
        System.out.println("Getting population results (in " + continent + "):");
        return getPopulation(query, "1_Population_Continent_" + continent +".md");
    }

    /**
     * The population of people, people living in cities, and people not living in cities in each region.
     * @return getPopulation(query)
     */
    public ArrayList<Results> getPopulationInRegion(){
        String query = "SELECT country.Region AS 'name', country.Population AS 'total_pop', " +
                "SUM(city.Population) AS 'city_pop', " +
                "(country.Population - SUM(city.Population)) AS nonCity_pop " +
                "FROM country JOIN city ON (city.CountryCode = country.Code)" +
                "GROUP BY country.Region, country.Population ORDER BY country.Population DESC;";
        System.out.println("Getting population results (in regions):");
        return getPopulation(query, "2_Population_Region.md");
    }

    /**
     * The population of people, people living in cities, and people not living in cities in each country.
     * @return getPopulation(query)
     */
    public ArrayList<Results> getPopulationInCountry(){
        String query = "SELECT country.Name AS 'name', country.Population AS 'total_pop', " +
                "SUM(city.Population) AS 'city_pop', " +
                "(country.Population - SUM(city.Population)) AS nonCity_pop " +
                "FROM country JOIN city ON (city.CountryCode = country.Code)" +
                "GROUP BY country.Name, country.Population ORDER BY country.Population DESC;";
        System.out.println("Getting population results (in countries):");
        return getPopulation(query, "3_Population_Country.md");
    }

    /**
     * The population of people in each district.
     * @return getPopulation(query)
     */
    public ArrayList<Results> getPopulationInDistrict(){
        String query = "SELECT city.District AS 'name', SUM(city.Population) AS 'total_pop', " +
                "SUM(city.Population) AS 'city_pop', " +
                "'none' AS nonCity_pop " +
                "FROM city " +
                "GROUP BY city.District ORDER BY SUM(city.Population) DESC;";
        System.out.println("Getting population results (in district):");
        return getPopulation(query, "4_Population_District.md");
    }

    /**
     * The population of people, people living in cities, and people not living in cities in the world.
     * @return getPopulation(query)
     */
    public ArrayList<Results> getPopulationInWorld(){
        String query = "SELECT 'Earth' AS 'name', SUM(country.Population) AS 'total_pop', " +
                        "(SELECT SUM(city.Population) FROM city JOIN country ON (country.Code = city.CountryCode)) AS 'city_pop', " +
                        "(SUM(country.Population) - (SELECT SUM(city.Population) FROM city JOIN country ON (country.Code = city.CountryCode))) AS nonCity_pop " +
                        "FROM country;";
        System.out.println("Getting population results (in the world):");
        return getPopulation(query, "5_Population_World.md");
    }


    // Helper methods

    /**
     *  Return list of results from a query in world db.
     * @param query
     * @return ResultSet of data or null
     * @throws Exception Failed to get results"
     */
    public ResultSet getResults(String query){
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(query);

            // Return results
            return rset;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *  Return list of country results extracted from ResultSet
     * @param rset
     * @return ArrayList of Results
     * @throws SQLException
     */
    public ArrayList<Results> getCountryList(ResultSet rset) throws SQLException{
        // Extract country information
        ArrayList<Results> ress = new ArrayList<Results>();
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
        return ress;
    }

    /**
     *  Return list of capital city results extracted from ResultSet
     * @param rset
     * @return ArrayList of Results
     * @throws SQLException
     */
    public ArrayList<Results> getCapitalList(ResultSet rset) throws SQLException{
        // Extract city information
        ArrayList<Results> ress = new ArrayList<Results>();
        while (rset.next()) {
            Results result = new Results();
            result.cityName = rset.getString("city.Name");
            result.population = rset.getInt("city.Population");
            result.countryCode = rset.getString("city.CountryCode");
            ress.add(result);
        }
        return ress;
    }

    /**
     *  Return list of city results extracted from ResultSet
     * @param rset
     * @return ArrayList of Results
     * @throws SQLException
     */
    public ArrayList<Results> getCityList(ResultSet rset) throws SQLException{
        // Extract city information
        ArrayList<Results> ress = new ArrayList<Results>();
        while (rset.next()) {
            Results result = new Results();
            result.cityName = rset.getString("city.Name");
            result.population = rset.getInt("city.Population");
            result.countryCode = rset.getString("city.CountryCode");
            result.district = rset.getString("city.District");
            ress.add(result);
        }
        return ress;
    }

    /**
     *  Return list of language results extracted from ResultSet
     * @param rset
     * @return ArrayList of Results
     * @throws SQLException
     */
    public ArrayList<Results> getLanguageList(ResultSet rset) throws SQLException{
        // Extract language information
        ArrayList<Results> ress = new ArrayList<Results>();
        while (rset.next()) {
            Results result = new Results();
            result.language = rset.getString("Language");
            result.speakers = rset.getInt("Speakers");
            result.world_percentage = Double.parseDouble(rset.getString("World_%"));
            ress.add(result);
        }
        return ress;
    }

    /**
     *  Return list of population results extracted from ResultSet
     * @param rset
     * @return ArrayList of Results
     * @throws SQLException
     */
    public ArrayList<Results> getPopulationList(ResultSet rset) throws SQLException{
        // Extract language information
        ArrayList<Results> ress = new ArrayList<Results>();
        while (rset.next()) {
            Results result = new Results();
            result.name = rset.getString("name");
            result.totalPop = rset.getLong("total_pop");
            result.cityPop = rset.getLong("city_pop");
            result.nonCityPop = rset.getString("nonCity_pop");
            ress.add(result);
        }
        return ress;
    }

    /**
     *  Return and print list of countries from a query in world db.
     * @param query
     * @return ArrayList<Results> of countries data or null
     * @throws Exception Failed to get country details"
     */
    public ArrayList<Results> getCountries(String query, String filename){
        try {
            // Get results
            ResultSet rset = getResults(query);

            // Extract country information
            ArrayList<Results> ress = getCountryList(rset);

            // Print results
            printCountryResults(ress);
            System.out.println(""); // Leave one line empty for clear view
            // Print to markdown
            printCountryToMarkdown(ress, filename);
            // Return list of results
            return ress;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     *  Return and print list of capital cities from a query in world db.
     * @param query
     * @return ArrayList<Results> of capital cities data or null
     * @throws Exception Failed to get capital city details"
     */
    public ArrayList<Results> getCapitals(String query, String filename){
        try {
            // Get results
            ResultSet rset = getResults(query);

            // Extract city information
            ArrayList<Results> ress = getCapitalList(rset);

            // Print results
            printCapitalResults(ress);
            System.out.println(""); // Leave one line empty for clear view
            // Print to markdown
            printCapitalToMarkdown(ress, filename);
            // Return list of results
            return ress;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details");
            System.out.println(""); // Leave one line empty for clear view
            return null;
        }
    }

    /**
     *  Return and print list of cities from a query in world db.
     *  It calls getCityList(rset) formating query results
     * @param query
     * @return ArrayList<Results> of cities data or null
     * @throws Exception Failed to get city details"
     */
    public ArrayList<Results> getCities(String query, String filename) {
        try {
            // Get results
            ResultSet rset = getResults(query);

            // Extract city information
            ArrayList<Results> ress = getCityList(rset);

            // Print results out
            printCityResults(ress);
            System.out.println(""); // Leave one line empty for clear view
            // Print to markdown
            printCityToMarkdown(ress, filename);
            // Return list of results
            return ress;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     *  Return and print list of languages from a query in world db.
     * @param query
     * @return ArrayList<Results> of cities data or null
     * @throws Exception Failed to get language details"
     */
    public ArrayList<Results> getLanguages(String query, String filename) {
        try {
            // Get results
            ResultSet rset = getResults(query);

            // Extract language information
            ArrayList<Results> ress = getLanguageList(rset);

            // Print out results if there are any
            printLanguageResults(ress);
            System.out.println(""); // Leave one line empty for clear view
            // Print to markdown
            printLanguageToMarkdown(ress, filename);
            // Return list of results
            return ress;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get language details");
            System.out.println(""); // Leave one line empty for clear view
            return null;
        }
    }

    /**
     *  Return and print population information from a query in world db.
     * @param query
     * @return ArrayList<Results> of population data or null
     * @throws Exception Failed to get language details"
     */
    public ArrayList<Results> getPopulation(String query, String filename){
        try {
            // Get results
            ResultSet rset = getResults(query);

            // Extract population information
            ArrayList<Results> ress = getPopulationList(rset);

            // Print out results if there are any
            printPopulationResults(ress);
            System.out.println(""); // Leave one line empty for clear view
            // Print to markdown
            printPopulationToMarkdown(ress, filename);
            // Return list of results
            return ress;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population details");
            System.out.println(""); // Leave one line empty for clear view
            return null;
        }
    }

    /**
     *  Created for integration test.It make query in world db with @param name,
     *  the same quary as in ArrayList<Results> getCityArray(String name), but I couldn't make it to work with the test
     * @param name of the city
     * @return result
     * @throws Exception Failed to get city details
     */
    public Results getCity(String name) {
        String query = "SELECT city.ID , city.Name, city.Population, city.CountryCode, city.District " +
                "FROM city " +
                "WHERE city.Name = '" + name + "'";
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Execute SQL statement
            ResultSet resultSet = stmt.executeQuery(query);
            // Extract city information
            if (resultSet.next())  {
                Results result = new Results();
                result.population = resultSet.getInt("city.Population");
                result.countryCode = resultSet.getString("city.CountryCode");
                result.district = resultSet.getString("city.District");
                return result;
            } else return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            System.out.println(""); // Leave one line empty for clear view
            return null;
        }
    }


    // Printing methods

    /**
     * Print country data: country code, name, continent, region, population, capital
     *  or "No results"
     * @param results - world db query
     */
    public void printCountryResults(ArrayList<Results> results){
        // Check results is not null
        if (!results.isEmpty())
        {
            // Print header
            System.out.println(String.format("%-10s %-40s %-10s %-8s %-40s %-8s",
                    "Country Code", "Country Name", "Continent", "Region", "Population", "Capital"));
            // Loop over all results in the list
            for (Results res : results)
            {
                if (results == null)
                    continue;
                String emp_string =
                        String.format("%-10s %-40s %-10s %-8s %-40s %-8s",
                                res.countryCode, res.countryName, res.continent, res.region, res.pop, res.capital);
                System.out.println(emp_string);
            }
        }else{
            System.out.println("No results");
            return;
        }
    }

    /**
     * Print capital city data: name, population, country code)
     *  or "No results"
     * @param results - world db query
     */
    public void printCapitalResults(ArrayList<Results> results) {
        // Check results is not null
        if (!results.isEmpty())
        {
            // Print header
            System.out.println(String.format("%-40s %-10s %-8s", "City Name", "City Population", "City Country Code"));
            // Loop over all results in the list
            for (Results res : results)
            {
                if (results == null)
                    continue;
                String emp_string = String.format("%-40s %-15s %-18s",res.cityName, res.population, res.countryCode);
                System.out.println(emp_string);
            }
        }else{
            System.out.println("No results");
            return;
        }
    }

    /**
     * Print city data: name, country code, district, population (000s)
     *  or "No results"
     * @param results - world db query
     */
    public void printCityResults(ArrayList<Results> results) {
        // Check results is not null
        if (!results.isEmpty()) {
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
        }else{
            System.out.println("No results");
            return;
        }
    }

    /**
     * Print language data: name, language name, number of speakers in the world, percentage of speakers in the world
     *  or "No results"
     * @param results - world db query
     */
    public void printLanguageResults(ArrayList<Results> results) {
        // Check if there are results
        if (!results.isEmpty())
        {
            // Print header
            System.out.println(String.format("%-15s %-13s %-10s",
                    "Language", "Speakers", "World %"));
            // Loop over all results in the list
            for (Results res : results)
            {
                if (results == null)
                    continue;
                String emp_string =
                        String.format("%-15s %-13s %-10s",
                                res.language,  res.speakers, res.world_percentage);
                System.out.println(emp_string);
            }
        }else{
            System.out.println("No results");
            return;
        }
    }

    /**
     * Print population data: name, total population, total population in cities, total population outside cities
     *  or "No results"
     * @param results - world db query
     */
    public void printPopulationResults(ArrayList<Results> results) {
        // Check if there are results
        if (!results.isEmpty())
        {
            // Print header
            System.out.println(String.format("%-25s %-20s %-20s %-20s",
                    "Name", "Total Population", "City Population", "Non City Population"));
            // Loop over all results in the list
            for (Results res : results)
            {
                if (results == null)
                    continue;
                String emp_string =
                        String.format("%-25s %-20s %-20s %-20s",
                                res.name,  res.totalPop, res.cityPop, res.nonCityPop);
                System.out.println(emp_string);
            }
        }else{
            System.out.println("No results");
            return;
        }
    }


    // Markdown Printing methods

    /**
     * Print country results to the markdown file
     * @param results
     * @param filename
     */
    public void printCountryToMarkdown(ArrayList<Results> results, String filename){
        if (!results.isEmpty()){
            StringBuilder sb = new StringBuilder();
            // Print header
            sb.append("| Country Code | Country Name | Continent | Region | Population | Capital |\r\n");
            sb.append("| --- | --- | --- | --- | --- | --- |\r\n");
            // Loop over all results in the list
            for (Results res : results) {
                if (res == null) continue;
                sb.append("| " + res.countryCode + " | " + res.countryName + " | " + res.continent + " | " + res.region + " | " + res.pop + " | " + res.capital + " |\r\n");
            }
            // Print to markdown file
            if(printToFile(sb, filename)){
                System.out.println("Country results printed to markdown: ./reports/" + filename);
            }
        }
    }

    /**
     * Print capital results to the markdown file
     * @param results
     * @param filename
     */
    public void printCapitalToMarkdown(ArrayList<Results> results, String filename){
        if (!results.isEmpty()){
            StringBuilder sb = new StringBuilder();
            // Print header
            sb.append("| City Name | City Population | City Country Code |\r\n");
            sb.append("| --- | --- | --- |\r\n");
            // Loop over all results in the list
            for (Results res : results) {
                if (res == null) continue;
                sb.append("| " + res.cityName + " | " + res.population + " | " + res.countryCode + " |\r\n");
            }
            // Print to markdown file
            if(printToFile(sb, filename)){
                System.out.println("Capital results printed to markdown: ./reports/" + filename);
            }
        }
    }

    /**
     * Print city results to the markdown file
     * @param results
     * @param filename
     */
    public void printCityToMarkdown(ArrayList<Results> results, String filename){
        if (!results.isEmpty()){
            StringBuilder sb = new StringBuilder();
            // Print header
            sb.append("| Name | Country Code | District | Population (000s) |\r\n");
            sb.append("| --- | --- | --- | --- |\r\n");
            // Loop over all results in the list
            for (Results res : results) {
                if (res == null) continue;
                sb.append("| " + res.cityName + " | " + res.countryCode + " | " + res.district + " | " + (res.population/1000) + " |\r\n");
            }
            // Print to markdown file
            if(printToFile(sb, filename)){
                System.out.println("City results printed to markdown: ./reports/" + filename);
            }
        }
    }

    /**
     * Print language results to the markdown file
     * @param results
     * @param filename
     */
    public void printLanguageToMarkdown(ArrayList<Results> results, String filename){
        if (!results.isEmpty()){
            StringBuilder sb = new StringBuilder();
            // Print header
            sb.append("| Language | Speakers | World |\r\n");
            sb.append("| --- | --- | --- |\r\n");
            // Loop over all results in the list
            for (Results res : results) {
                if (res == null) continue;
                sb.append("| " + res.language + " | " + res.speakers + " | " + res.world_percentage + " |\r\n");
            }
            // Print to markdown file
            if(printToFile(sb, filename)){
                System.out.println("Language results printed to markdown: ./reports/" + filename);
            }
        }
    }

    /**
     * Print population results to the markdown file
     * @param results
     * @param filename
     */
    public void printPopulationToMarkdown(ArrayList<Results> results, String filename){
        if (!results.isEmpty()){
            StringBuilder sb = new StringBuilder();
            // Print header
            sb.append("| Name | Total Population | City Population | Non City Population |\r\n");
            sb.append("| --- | --- | --- | --- |\r\n");
            // Loop over all results in the list
            for (Results res : results) {
                if (res == null) continue;
                sb.append("| " + res.name + " | " + res.totalPop + " | " + res.cityPop + " | " + res.nonCityPop + " |\r\n");
            }
            // Print to markdown file
            if(printToFile(sb, filename)){
                System.out.println("Population results printed to markdown: ./reports/" + filename);
            }
        }
    }

    /**
     * Write given string to the file
     * @param sb
     * @param filename
     * @returns boolean
     * @throws IOException e
     */
    public boolean printToFile(StringBuilder sb, String filename){
        try{
            // Create and write to markdown file
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            writer.write(sb.toString());
            writer.close();
            return true;
        } catch (IOException e){
            System.out.println("Failed to print to file");
            e.printStackTrace();
            return false;
        }
    }
}