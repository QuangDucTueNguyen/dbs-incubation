package com.incubator.dbs.authenticateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class AuthenticateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticateServiceApplication.class, args);
	}

}
