package com.aeroport.demo.repos;

import com.aeroport.demo.DB.DBC;
import com.aeroport.demo.DemoApplication;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Passenger {
    enum Names{Pavel,Grisha,Mo,Roma,Vigo,Torin,Ruslan,Tur,Mari,Victor,Bow}

    public static void addRandom(int flight_id) {
        try {
            PreparedStatement stmnt = DBC.conn.prepareStatement("insert into passenger (phonenum,flight_id,name) values (?,?,?)", Statement. RETURN_GENERATED_KEYS );
            stmnt.setString(1,"+79"+ DemoApplication.random.nextInt(899999999)+100000000);
            stmnt.setInt(2,flight_id);
            stmnt.setString(3,Names.values()[DemoApplication.random.nextInt(Passenger.Names.values().length)].toString() + DemoApplication.random.nextInt(100000));
            stmnt.executeUpdate();
            ResultSet rs=stmnt.getGeneratedKeys();
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public String toString(int passenger_id){
        try {
            String sql = "select * from passenger where passenger_id = ?";
            PreparedStatement st = DBC.conn.prepareStatement(sql);
            st.setInt(1,passenger_id);
            ResultSet rs = st.executeQuery();
            return rs.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(10006);
        }
        return null;
    }
}
