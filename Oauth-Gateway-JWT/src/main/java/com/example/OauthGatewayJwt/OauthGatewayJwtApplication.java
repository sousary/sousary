package com.example.OauthGatewayJwt;
import com.example.OauthGatewayJwt.config.AuthenticationFilter;
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


@EnableDiscoveryClient
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@Configuration
public class OauthGatewayJwtApplication {
	private final AuthenticationFilter authenticationFilter;
	private AuthenticationFilter.Config config;

	public static void main(String[] args) {
		SpringApplication.run(OauthGatewayJwtApplication.class, args);
	}
	@Bean
	public RouteLocator apiRoutes(RouteLocatorBuilder builder) {
		log.info("move to route locator");
		RouteLocator route =builder.routes()
				.route("resource-server",p -> p
						.path("/api/users")
						.filters(f -> f.filter((authenticationFilter.apply(config))))
						.uri("http://localhost:8090"))
				.route("oauth-server"
						,p -> p
						.path("/oauth2/**")
						.uri("http://127.0.0.1:9000"))
				.route("client-server"
						,p -> p
						.path("/api/**")
						.uri("http://127.0.0.1:8088"))
				.build();
		log.info("------------------------>"+route);

		return route;
	}


}
