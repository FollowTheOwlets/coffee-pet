package com.example.coffee_pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestCoffeePetApplication {

	public static void main(String[] args) {
		SpringApplication.from(CoffeePetApplication::main).with(TestCoffeePetApplication.class).run(args);
	}

}
