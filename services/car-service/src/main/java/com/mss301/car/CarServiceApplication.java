package com.mss301.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mss301"})
public class CarServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

}
