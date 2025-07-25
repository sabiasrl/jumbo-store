package com.jumbo.stores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jumbo.stores")
public class JumboStoresFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(JumboStoresFinderApplication.class, args);
	}
}