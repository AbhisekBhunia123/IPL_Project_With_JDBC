package prac.dao;

import prac.service.Connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Pair implements Comparable<Pair>{
    String bowler;
    float economy;
    Pair(String bowler,float economy){
        this.bowler=bowler;
        this.economy=economy;
    }
    public int compareTo(Pair o){
        return Double.compare(this.economy,o.economy);
    }
}
public class QueryResult {
    public static void getMatchesPlayedInYear()throws  Exception{
        Connection con = Connector.makeConnection();
        String query = "select season,count(*) from match group by season";
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        while(rset.next()){
            System.out.print("( "+rset.getString(1)+" => "+rset.getString(2)+" ), ");
        }
        System.out.println();
    }

    public static void getNumberOfOwnMatchesInYear() throws Exception{
        Connection con = Connector.makeConnection();
        String query = "select winner,count(*) from match group by winner having winner is not null";
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        while(rset.next()){
            System.out.print("( "+rset.getString(1)+" => "+rset.getString(2)+" ), ");
        }
        System.out.println();
    }

    public static void getExtraRunsConductedPerTeamInYear(int year)throws Exception{
        Connection con = Connector.makeConnection();
        String query = "select delivery.batting_team,sum(cast(delivery.extra_runs as integer)) from delivery inner join match on delivery.match_id = match.id where match.season='2016' and not delivery.extra_runs = '0' group by batting_team";
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        while(rset.next()){
            System.out.print("( "+rset.getString(1)+" => "+rset.getString(2)+" ), ");
        }
        System.out.println();
    }

    public static void getEconomicBowlerInYear(int year)throws  Exception{
        Connection con = Connector.makeConnection();
        HashMap<String,Integer> bowlerRuns = new HashMap<>();
        HashMap<String,Integer> bowlerBalls = new HashMap<>();
        ArrayList<Pair> economyBlower = new ArrayList<>();
        String query1 = "select delivery.bowler,sum(cast(delivery.total_runs as integer)) from delivery inner join match on delivery.match_id = match.id where match.season='2015' and not delivery.total_runs = '0' group by delivery.bowler";
        String query2 = "select delivery.bowler,count(delivery.bowler) from delivery inner join match on delivery.match_id = match.id where match.season='2015' and delivery.wide_runs = '0' and delivery.noball_runs = '0' group by delivery.bowler";

        Statement stmt1 = con.createStatement();
        Statement stmt2 = con.createStatement();
        ResultSet rset1 = stmt1.executeQuery(query1);
        ResultSet rset2 = stmt2.executeQuery(query2);
        while(rset1.next()){
            bowlerRuns.put(rset1.getString(1),Integer.parseInt(rset1.getString(2)));
        }
        while(rset2.next()){
            bowlerBalls.put(rset2.getString(1),Integer.parseInt(rset2.getString(2)));
        }
        for(String bowler:bowlerBalls.keySet()){
            economyBlower.add(new Pair(bowler,bowlerRuns.get(bowler)/(bowlerBalls.get(bowler)/6f)));
        }
        Collections.sort(economyBlower);
        for(Pair p:economyBlower){
            System.out.print("( "+p.bowler+" -> "+p.economy+" )");
        }
        System.out.println();
    }
}
