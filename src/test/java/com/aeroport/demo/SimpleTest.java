package com.aeroport.demo;


import java.util.Random;

public class SimpleTest {

    enum Names{Pavel,Grisha,Roma,Vigo,Ruslan,Mari,Victor}
    private static final Random random = new Random();
    public static void main(String[] args){

        System.out.println("Hello world");

        System.out.println(Names.values()[random.nextInt(Names.values().length)].toString());

        String sql = "select * from flight order by landingtime desc";

        System.out.println(sql);
    }
}
