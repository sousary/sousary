package com.example.OauthGatewayJwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.cloud.gateway.actuate.GatewayControllerEndpoint;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
@Controller
@RestControllerEndpoint(id = "gateway/routes")
@Slf4j
public class GatewayRoutesPropertiesController {

    private final GatewayControllerEndpoint gatewayControllerEndpoint;
    private final RouteDefinitionRepository routeDefinitionRepository;

    @Autowired
    public GatewayRoutesPropertiesController(GatewayControllerEndpoint gatewayControllerEndpoint,
                                             RouteDefinitionRepository routeDefinitionRepository) {
        this.gatewayControllerEndpoint = gatewayControllerEndpoint;
        this.routeDefinitionRepository = routeDefinitionRepository;
    }

    @GetMapping("/save-properties")
    public ResponseEntity<String> saveGatewayRoutesProperties() {
        log.info("---------------------> move to controller");
        List<RouteDefinition> routes = (List<RouteDefinition>) routeDefinitionRepository.getRouteDefinitions();

        Properties properties = new Properties();
        for (RouteDefinition route : routes) {
            String routeId = route.getId();
            String uri=route.getUri().toString();
            properties.setProperty(routeId + ".uri", route.getUri().toString());

            log.info("route----------------------------------->"+routeId);
            log.info("uri---------------------------------------->"+uri);

            // Add more route properties as needed
        }

        try (FileWriter fileWriter = new FileWriter("gateway-routes.properties")) {
            log.info("------------------------->move to save file");
            properties.store(fileWriter, "Gateway Routes");
        } catch (IOException e) {
            // Handle file writing exception
            return ResponseEntity.status(500).body("Error occurred while saving properties.");
        }

        return ResponseEntity.ok("Gateway routes properties saved successfully.");
    }
}

