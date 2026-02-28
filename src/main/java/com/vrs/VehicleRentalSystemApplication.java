package com.vrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VehicleRentalSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleRentalSystemApplication.class, args);
	}

}