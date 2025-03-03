package com.legacyinternational.globalyouthleadership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.legacyinternational.globalyouthleadership.infrastructure.repositories")
public class GlobalyouthleadershipApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalyouthleadershipApplication.class, args);
	}

}
