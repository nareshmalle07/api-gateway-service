package com.amazon.amazon_api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmazonApiGatewayApplication {

	public static void main(String[] args) {
		System.out.println("Api gateway started ");
		SpringApplication.run(AmazonApiGatewayApplication.class, args);
		System.out.println("Api gateway eneded ");
	}

}
