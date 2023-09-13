package prac.main;

import prac.dao.QueryResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try{
            System.out.println("Enter 1 to get Number of matches played per year of all the years in IPL. =>");
            System.out.println("Enter 2 to get Number of matches won of all teams over all the years of IPL. =>");
            System.out.println("Enter 3 to get For the year 2016 get the extra runs conceded per team. =>");
            System.out.println("Enter 4 to get For the year 2015 get the top economical bowlers. =>");
            Scanner sc = new Scanner(System.in);
            int number = sc.nextInt();
            switch (number){
                case 1:
                    QueryResult.getMatchesPlayedInYear();
                    break;
                case 2:
                    QueryResult.getNumberOfOwnMatchesInYear();
                    break;
                case 3:
                    QueryResult.getExtraRunsConductedPerTeamInYear(2016);
                    break;
                case 4:
                    QueryResult.getEconomicBowlerInYear(2015);
                    break;
                default:
                    System.out.println("Your response is out of query!!");

            }

        }catch(Exception e){ System.out.println(e);}
    }
}
