package com.aeroport.demo.repos;


import com.aeroport.demo.DB.DBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LandingStrip {
    public static void addRandom() {
        DBC.execSQL(
                "insert into landingstrip (timetoempty) values (null)"
        );
    }
    public String toString(int landingstrip_id){
        try {
            String sql = "select * from landingstrip where landingstrip_id = ?";
            PreparedStatement st = DBC.conn.prepareStatement(sql);
            st.setInt(1,landingstrip_id);
            ResultSet rs = st.executeQuery();
            return rs.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(10006);
        }
        return null;
    }
}
