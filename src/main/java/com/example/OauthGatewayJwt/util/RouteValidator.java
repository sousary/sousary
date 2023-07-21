package com.example.OauthGatewayJwt.util;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    //----------------------------->devide route that need validate access token----------------//
    public static final List<String> openApiEndpoints = List.of(
            "/api/**",
            "/eureka"

    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}

