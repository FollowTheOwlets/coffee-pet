package com.example.coffee_pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoffeePetApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeePetApplication.class, args);
	}

}
