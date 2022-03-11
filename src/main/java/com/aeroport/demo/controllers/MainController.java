package com.aeroport.demo.controllers;

import com.aeroport.demo.DB.DBC;
import com.aeroport.demo.DemoApplication;
import com.aeroport.demo.repos.Flight;
import com.aeroport.demo.repos.Passenger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("text", "Эта работа написана Лапчиком Павлом Za Warudo");
        return "home";
    }

    @Scheduled(fixedDelay = 10000)
    public void scheduleFixedDelayTask() {
        System.out.println("-------------");
        String sql;
        Date date = new Date();

        // update landingstrip set flight_id = null where timetoempty < NOW()
        try {
            sql = "update landingstrip set flight_id = NULL where timetoempty < ?";
            PreparedStatement st = DBC.conn.prepareStatement(sql);
            st.setLong( 1, date.getTime() / 1000 );
            st.executeUpdate();
            st.close();
        } catch (SQLException er) {
            System.out.println(er.getMessage());
            er.printStackTrace();
            System.exit(10003);
        }

        sql = "select flight_id,name,landingtime from flight where landingtime < ?";
        try {
            PreparedStatement st = DBC.conn.prepareStatement(sql);
            st.setLong( 1, date.getTime() / 1000 );
            ResultSet rs = st.executeQuery();
            //st.close();
            while (rs.next()) {
                String name = rs.getString(2);
                System.out.println("Прилетел " + name);
                // select * from landingstrip where flight_id is null
                sql = "select * from landingstrip where flight_id is null limit 1";
                Statement st2 = DBC.conn.createStatement();
                ResultSet rs2 = st2.executeQuery(sql);
                //st2.close();
                if (rs2.next()) {
                    // ЕСЛИ ЕСТЬ СВОБОДНАЯ ПОЛОСА
                    // у нас теперь landingstrip_id свободной полосы
                    // update landingstrip set flight_id = ?flight_id, timetoempty=NOW()+3min where landingstrip_id = ?landingstrip_id
                    sql = "update landingstrip set flight_id = ?, timetoempty = ? where landingstrip_id = ?";
                    PreparedStatement st3 = DBC.conn.prepareStatement(sql);
                    st3.setInt(1, rs.getInt(1));
                    st3.setLong( 2, (date.getTime() / 1000) + 180);
                    st3.setInt(3, rs2.getInt(1));
                    st3.executeUpdate();
                    st3.close();
                    // delete from passengers where flight_id = ?
                    sql = "delete from passenger where flight_id = ?";
                    PreparedStatement st41 = DBC.conn.prepareStatement(sql);
                    st41 = DBC.conn.prepareStatement(sql);
                    st41.setInt(1, rs.getInt(1));
                    st41.execute();
                    st41.close();
                    // delete from flight where flight_id = ?flight_id
                    sql = "delete from flight where flight_id = ?";
                    PreparedStatement st4 = DBC.conn.prepareStatement(sql);
                    st4 = DBC.conn.prepareStatement(sql);
                    st4.setInt(1, rs.getInt(1));
                    st4.execute();
                    st4.close();
                    int flight_id = Flight.addRandom();
                    for(int p = 0; p< DemoApplication.random.nextInt(20)+1; p++) {
                        Passenger.addRandom(flight_id);
                    }
                } else {
                    // ЕСЛИ НЕТ СВОБОДНОЙ ПОЛОСЫ
                    System.out.println("НЕТ СВОБОДНОЙ ПОЛОСЫ");
                    // update flight set landingtime = ? , delayed = delayed+1 where flight_id = ?
                    sql = "update flight set landingtime = ? , delayed = delayed+1 where flight_id = ?";
                    PreparedStatement st5 = DBC.conn.prepareStatement(sql);
                    st5 = DBC.conn.prepareStatement(sql);
                    st5.setLong( 1, (date.getTime() / 1000) + 600);
                    st5.setInt(2, rs.getInt(1));
                    st5.executeUpdate();
                    st5.close();
                    // for select * from passenger where flight_id = ?flight_id
                    sql = "select * from passenger where flight_id = ?";
                    PreparedStatement st6 = DBC.conn.prepareStatement(sql);
                    st6 = DBC.conn.prepareStatement(sql);
                    st6.setInt(1, rs.getInt(1));
                    ResultSet rs6 = st6.executeQuery();
                    //st6.close();
                    //      insert into sms (flight_id,passenger_id,phonenum,smstext) values (...)
                    while (rs6.next()){
                        sql = "insert into sms (flight_id,passenger_id,phonenum,smstext) values ( ? , ? , ? , ? )";
                        PreparedStatement st7 = DBC.conn.prepareStatement(sql);
                        st7 = DBC.conn.prepareStatement(sql);
                        st7.setInt(1, rs.getInt(1));
                        st7.setInt(2, rs6.getInt(1));
                        st7.setString(3, rs6.getString(3));
                        st7.setString(4, "Рейс "+name+" задерживается");
                        st7.execute();
                        st7.close();
                    }
                }
            }
        } catch (SQLException er) {
            System.out.println(er.getMessage());
            er.printStackTrace();
            System.exit(10002);
        }
    }
}
