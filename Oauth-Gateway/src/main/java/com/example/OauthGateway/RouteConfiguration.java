//package com.example.OauthGateway;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@RefreshScope
//@Configuration
//@Slf4j
//public class RouteConfiguration {
//    @Bean
//    public RouteLocator apiRoutes(RouteLocatorBuilder builder) {
//        log.info("move to route locator");
//
//        RouteLocator route =builder.routes()
//                .route("resource-server",p -> p
//                        .path("/api/**")
//                        .uri("lb"))
//                .route("client-server"
//                        ,p -> p
//                        .path("/api/**")
//                        .uri("localhost:CLIENT-SERVER:8088"))
//                .build();
//        log.info("------------------------>"+route);
//
//        return route;
//    }
//
//}
