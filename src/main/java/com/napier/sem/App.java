package com.napier.sem;

import com.napier.sem.country.Country;

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


    public static void main(String[] args) throws SQLException {

        Languages first = new Languages();
        System.out.println("----------------------------------------------");

        ArrayList<LanguageData> chinese = new ArrayList<>();
        ArrayList<Country> countries = new ArrayList<>();
        int chineseSpeakers=0;

        chinese = first.chinese();
        countries = first.countriesData();

        for (LanguageData ld: chinese
             ) {
            System.out.println(ld.toString());
          String x = ld.getCountryCode();
          float i = ld.getPercentage()/100;
            for (Country c: countries
                 ) {if(c.getCode().equals(x)){
                     int pop = c.getPopulation();
                //System.out.println(c.getPopulation());
                     int res = (int) (pop*i);
                chineseSpeakers += res;
            }


            }
        }
        System.out.println("Chinese speakers in a world: " + chineseSpeakers);




            }

        }




