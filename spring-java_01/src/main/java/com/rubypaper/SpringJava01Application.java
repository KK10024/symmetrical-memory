package com.rubypaper;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringJava01Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringJava01Application.class, args);
	}

}
