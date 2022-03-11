package com.aeroport.demo;

import com.aeroport.demo.DB.DBC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	public static final Random random = new Random(System.currentTimeMillis());

	public static void main(String[] args) {
		DBC.open();

		SpringApplication.run(DemoApplication.class, args);
	}

}
