package com.skyrocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SkyrocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkyrocketApplication.class, args);
		//DatabaseConnector.DBConnector.connect();
		//DatabaseConnector.DBConnector.prepareTables();
	}
}
