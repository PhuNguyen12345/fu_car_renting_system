package com.mss301.renting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mss301"})
public class RentingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentingServiceApplication.class, args);
	}

}
