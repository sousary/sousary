package com.example.OauthGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;


@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFluxSecurity
public class OauthGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthGatewayApplication.class, args);
	}

}
