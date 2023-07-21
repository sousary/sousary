package com.example.OauthGatewayJwt;
import com.example.OauthGatewayJwt.config.AuthenticationFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;


@EnableDiscoveryClient
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@Component
@Configuration
public class OauthGatewayJwtApplication {
	private final AuthenticationFilter authenticationFilter;
	private AuthenticationFilter.Config config;

	public static void main(String[] args) {
		SpringApplication.run(OauthGatewayJwtApplication.class, args);

//		log.info("move to save route------------------>");
//		String actuatorEndpointUrl = "http://localhost:9001/actuator/gateway/routes";
//		String filePath = "gateway-route.txt";
//
//		// Send GET request to Actuator endpoint
//		RestTemplate restTemplate = new RestTemplate();
//		String response = restTemplate.getForObject(actuatorEndpointUrl, String.class);
//
//		Properties properties = new Properties();
////        properties.setProperty("route1.property1", "value1");
////        properties.setProperty("route2.property2", "value2");
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			JsonNode responseJson = objectMapper.readTree(response);
//
//			// Extract properties from the JSON response
//			JsonNode routesNode = responseJson.get("routes");
//			for (JsonNode routeNode : routesNode) {
//				String routeId = routeNode.get("id").asText();
//				String predicate=routeNode.get("predicate").asText();
//				String uri = routeNode.get("uri").asText();
//				log.info("--------------------->route============"+routeId);
//				log.info("uri--------------------------------------->"+uri);
//				log.info("------------------------>predicate"+predicate);
//
//				// Add the extracted properties to the Properties object
//				properties.setProperty(routeId + ".uri", uri);
//
//				// Add more route properties as needed
//			}
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//
//		try (FileWriter fileWriter = new FileWriter(filePath)) {
//			log.info("--------------------> move to save file");
//			properties.store(fileWriter, "Gateway Routes Properties");
//			System.out.println("Gateway routes properties saved to " + filePath);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

//
	}

//	@Bean
//	public RouteLocator apiRoutes(RouteLocatorBuilder builder) {
//		log.info("move to route locator");
//		RouteLocator route =builder.routes()
//				.route("resource-server",p -> p
//						.path("/api/users")
//						.filters(f -> f.filter((authenticationFilter.apply(config))))
//						.uri("http://localhost:8090"))
//				.route("oauth-server"
//						,p -> p
//						.path("/oauth2/**")
//						.uri("http://127.0.0.1:9000"))
//				.route("client-server"
//						,p -> p
//						.path("/api/**")
//						.uri("http://127.0.0.1:8088"))
//				.build();
//		log.info("------------------------>"+route);
//
//		return route;
//	}


}

