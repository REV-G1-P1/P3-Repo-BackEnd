package com.revature.p3bankbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.revature.models")
@EnableJpaRepositories("com.revature.repositories")
@ComponentScan("com.revature")
@ServletComponentScan
@SpringBootApplication
public class P3BankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(P3BankBackendApplication.class, args);
	}

}
