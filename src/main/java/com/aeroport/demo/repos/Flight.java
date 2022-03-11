package com.aeroport.demo.repos;

import com.aeroport.demo.DB.DBC;
import com.aeroport.demo.DemoApplication;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Flight {
    public static int addRandom(){
        try {
            PreparedStatement stmnt = DBC.conn.prepareStatement("INSERT INTO flight(name,landingtime,delayed) VALUES (?,?,?)", Statement. RETURN_GENERATED_KEYS );
            stmnt.setString(1,((char)(65+ DemoApplication.random.nextInt(23))) + "-" + (DemoApplication.random.nextInt(899) + 100));
            Date date = new Date();
            stmnt.setLong(2, date.getTime() / 1000 + DemoApplication.random.nextInt(60*60*3) + 60 - 60*90);
            stmnt.setInt(3,0);
            stmnt.executeUpdate();
            ResultSet rs=stmnt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public String toString(int flight_id){
        try {
            String sql = "select * from flight where flight_id = ?";
            PreparedStatement st = DBC.conn.prepareStatement(sql);
            st.setInt(1,flight_id);
            ResultSet rs = st.executeQuery();
            return rs.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(10004);
        }
        return null;
    }
}
