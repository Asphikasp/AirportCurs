package com.aeroport.demo.controllers;

import com.aeroport.demo.DB.DBC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class FlightTable {

    public int flight_id;
    public String name;
    public String landingtimeString;
    public int delayed;

    private List<FlightTable> getAll() {
        List<FlightTable> lft = new ArrayList<FlightTable>();
        String sql = "select flight_id,name,landingtime,delayed from flight order by landingtime";
        try {
            Statement st = DBC.conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                FlightTable t = new FlightTable();
                t.flight_id = rs.getInt(1);
                t.name = rs.getString(2);
                Date date = new Date(rs.getLong(3) * 1000);
                t.landingtimeString = date.toString();
                t.delayed = rs.getInt(4);
                lft.add(t);
            }
            return lft;
        } catch (SQLException er) {
            System.out.println(er.getMessage());
            System.exit(10002);
        }
        return new ArrayList<FlightTable>();
    }

    @GetMapping("/table")
    public String table(Model model) {
        model.addAttribute("Flights", getAll());
        return "table";
    }
}
