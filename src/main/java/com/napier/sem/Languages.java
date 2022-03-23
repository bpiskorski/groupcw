package com.napier.sem;


import com.napier.sem.country.Country;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Languages {

private Connection con;



 public Connection connect() {
   Connection con = null;
  try {
   // Create an SQL statement
   ConnectionManager cM = new ConnectionManager();

  con = cM.getNewConnect("db", 3000);
  } catch (Exception e) {
   e.printStackTrace();
  }
  return con;
 }


 //public void chineseSpeakers() {


 public ArrayList<LanguageData> chinese() {

  StringBuilder sb = new StringBuilder();
  Connection con = connect();

  System.out.println("getting data from database");
  //LanguageData lg = new LanguageData();
  ArrayList<LanguageData> lgArr = new ArrayList<>();
  String sql = "select * from world.countrylanguage WHERE language = 'Chinese' ORDER BY percentage DESC";

  Statement stmt = null;
  try {
   stmt = con.createStatement();

   ResultSet rset = stmt.executeQuery(sql);

   while (rset.next()) {
    String countryCode = rset.getString("countryCode");
    String language = rset.getString("language");
    String isOfficial = rset.getString("isOfficial");
    float percentage = rset.getFloat("percentage");
    LanguageData lg = new LanguageData(countryCode, language, isOfficial, percentage);
    lgArr.add(lg);
    //System.out.println(lg);
    //sb.append(lg.toString()).append("\r\n");

    //System.out.println(lg.toString());
   }

  } catch (SQLException throwables) {
   throwables.printStackTrace();
  }
  return lgArr;
 }


 public ArrayList<Country> countriesData() {

  Connection con = connect();
  System.out.println("Creating the report...");
  StringBuilder sb = new StringBuilder();
  ArrayList<Country> cArr = new ArrayList<>();

  try {
   // Create an SQL statement
   Statement stmt = con.createStatement();

   String sql = "select * from world.country";

   ResultSet rset = stmt.executeQuery(sql);

   while (rset.next()) {

    String code = rset.getString("code");
    String name = rset.getString("name");
    String continent = rset.getString("Continent");
    String region = rset.getString("region");
    Integer population = rset.getInt("Population");
    Integer capital = rset.getInt("capital");
    Country country = new Country(code, name, continent, region, population, capital);
   // sb.append(country.toString()).append("\r\n");
    //System.out.println(country.toString());
    cArr.add(country);
   }


  } catch (SQLException throwables) {
   throwables.printStackTrace();
  }
  return cArr;
 }

}



//INSERT INTO `country` VALUES ('CHN','China','Asia','Eastern Asia',9572900.00,-1523,1277558000,71.4,982268.00,917719.00,'Zhongquo','People\'sRepublic','Jiang Zemin',1891,'CN')

/*  `Code` char(3) NOT NULL DEFAULT '',
          `Name` char(52) NOT NULL DEFAULT '',
          `Continent` enum('Asia','Europe','North America','Africa','Oceania','Antarctica','South America') NOT NULL DEFAULT 'Asia',
        `Region` char(26) NOT NULL DEFAULT '',
        `SurfaceArea` decimal(10,2) NOT NULL DEFAULT '0.00',
        `IndepYear` smallint DEFAULT NULL,
        `Population` int NOT NULL DEFAULT '0',
        `LifeExpectancy` decimal(3,1) DEFAULT NULL,
        `GNP` decimal(10,2) DEFAULT NULL,
        `GNPOld` decimal(10,2) DEFAULT NULL,
        `LocalName` char(45) NOT NULL DEFAULT '',
        `GovernmentForm` char(45) NOT NULL DEFAULT '',
        `HeadOfState` char(60) DEFAULT NULL,
        `Capital` int DEFAULT NULL,
        `Code2` char(2) NOT NULL DEFAULT '',
        PRIMARY KEY (`Code`)*/


