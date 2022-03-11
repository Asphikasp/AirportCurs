package com.aeroport.demo.repos;

import com.aeroport.demo.DB.DBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sms {
    public static void sendSms(int flight_id,int passenger_id,String smsText) {
        try {
            PreparedStatement stmnt = DBC.conn.prepareStatement("insert into sms (flight_id,passenger_id,smstext) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmnt.setInt(1, flight_id);
            stmnt.setInt(2, passenger_id);
            stmnt.setString(3, smsText);
            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String toString(int flight_id, boolean flight,int passenger_id, boolean passenger){
        String str ="";

        try {
            String sql = "select * from flight where flight_id = ?";
            PreparedStatement st = DBC.conn.prepareStatement(sql);
            st.setInt(1,flight_id);
            ResultSet rs = st.executeQuery();
            str+= rs.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(10008);
        }
        str+= "\n";
        try {
            String sql = "select * from passenger where passenger_id = ?";
            PreparedStatement st = DBC.conn.prepareStatement(sql);
            st.setInt(1,passenger_id);
            ResultSet rs = st.executeQuery();
            str+= rs.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(10009);
        }
        return str;
    }
}
