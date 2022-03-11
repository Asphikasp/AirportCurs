package com.aeroport.demo.DB;
import com.aeroport.demo.DemoApplication;
import com.aeroport.demo.repos.Flight;
import com.aeroport.demo.repos.LandingStrip;
import com.aeroport.demo.repos.Passenger;

import java.sql.*;

public class DBC {
    public static Connection conn;

    public static void open() {
        String url = "jdbc:sqlite:C:\\Users\\Pavel\\IdeaProjects\\demo\\src\\main\\java\\com\\aeroport\\Airport.db";
        try {
            conn = DriverManager.getConnection(url);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from flight");
            st.close();
        } catch (SQLException er) {
            if (er.getErrorCode() == 1) {
                //db is empty
                createStructure();
            }
            System.out.println(er.getMessage());
            System.out.println(er.getErrorCode());
        }
    }

    public static void createStructure() {
        execSQL(
            "CREATE TABLE IF NOT EXISTS flight (\n"
            + "	flight_id integer PRIMARY KEY,\n"
            + "	name varchar(20) NOT NULL,\n"
            + "	landingtime integer,\n"
            + "	delayed integer\n"
            + ");"
        );
        execSQL(
            "CREATE TABLE IF NOT EXISTS landingstrip (\n"
            + "	landingstrip_id integer PRIMARY KEY,\n"
            + "	flight_id integer,\n"
            + "	timetoempty integer\n"
            + ");"
        );
        execSQL(
            "CREATE TABLE IF NOT EXISTS passenger (\n"
            + "	passenger_id integer PRIMARY KEY,\n"
            + "	flight_id integer,\n"
            + "	phonenum varchar(20),\n"
            + "	name varchar(200)\n"
            + ");"
        );
        execSQL(
            "CREATE TABLE IF NOT EXISTS sms (\n"
            + "	flight_id integer,\n"
            + "	passenger_id integer,\n"
            + "	phonenum varchar(20),\n"
            + "	smstext text\n"
            + ");"
        );
        DBC.fillRandom();
    }

    public static void execSQL(String sql) {
        Statement st = null;
        try {
            st = conn.createStatement();
            st.execute(sql);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(sql);
            System.exit(10001);
        }
    }

    public static void fillRandom(){
        for(int i = 0; i< DemoApplication.random.nextInt(4)+1; i++) {
            LandingStrip.addRandom();
        }
        for(int i = 0;i< DemoApplication.random.nextInt(12)+8;i++) {
            int flight_id = Flight.addRandom();
            for(int p = 0;p< DemoApplication.random.nextInt(20)+1;p++) {
                Passenger.addRandom(flight_id);
            }
        }
    }
}


