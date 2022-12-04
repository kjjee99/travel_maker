package com.travelmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelMakerApplication {

	static {
		System.setProperty("com.amazonaws.sdk.disableEC2Metadata", "true");
	}

	public static void main(String[] args) {
		SpringApplication.run(TravelMakerApplication.class, args);
	}

}
