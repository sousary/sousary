package com.example.OauthGatewayJwt.config;
import com.example.OauthGatewayJwt.util.JwtUtil;
import com.example.OauthGatewayJwt.util.RouteValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j

public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            log.info("--------------------> Gateway filter<-----------------------------");
            ServerHttpResponse response=exchange.getResponse();
            ServerHttpRequest request=exchange.getRequest();

            Map<String,Object> result=new HashMap<>();
            ObjectMapper objectMapper=new ObjectMapper();

            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND);
            boolean right=responseStatusException.equals(HttpStatus.NOT_FOUND);

            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println(timestamp);

            if (right) {
                log.info("----------------------->not found");
                log.info("The Url is not found!");
                result.put("timestamp",timestamp.toString());
                result.put("message","Url Not Found");
                result.put("status", HttpStatus.NOT_FOUND.value());
                response.setStatusCode(HttpStatus.NOT_FOUND);
                DataBuffer buffer = null;
                try {
                    buffer = exchange.getResponse().bufferFactory().wrap(objectMapper.writeValueAsBytes(result));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return response.writeWith(Flux.just(buffer));
            }
            else {
                if (validator.isSecured.test(exchange.getRequest())) {
                    if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION) || request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).isEmpty() || !request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).startsWith("Bearer ")) {
                        log.info("--------------------------------------------> move to condition <--------------------------");
                        try {
                            log.info("Token is empty or invalid format");
                            result.put("timestamp", timestamp.toString());
                            result.put("message", "Token Not Found");
                            result.put("status", HttpStatus.UNAUTHORIZED.value());
                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
                            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(objectMapper.writeValueAsBytes(result));
                            return response.writeWith(Flux.just(buffer));

                        } catch (Exception e) {
                            throw new RuntimeException();
                        }
                    } else {

                        String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                        if (authHeader.startsWith("Bearer ")) {
                            String token = authHeader.substring(7);
                            log.info("Try to Validate Token ======> " + token);
                            try {
                                Boolean isTokenValid = jwtUtil.validateToken(token);
                                log.info("isTokenValid =======> " + isTokenValid);
                                if (!isTokenValid) {
                                    result.put("timestamp", timestamp.toString());
                                    result.put("message", "Unauthorized Request");
                                    result.put("status", HttpStatus.UNAUTHORIZED.value());
                                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                                    DataBuffer buffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(result));
                                    return response.writeWith(Flux.just(buffer));
                                }
                            } catch (Exception e) {
                                result.put("timestamp", timestamp.toString());
                                result.put("message", "Invalid Token");
                                result.put("status", HttpStatus.UNAUTHORIZED.value());
                                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                                DataBuffer buffer = null;
                                try {
                                    buffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(result));
                                } catch (JsonProcessingException ex) {
                                    throw new RuntimeException(ex);
                                }
                                return response.writeWith(Flux.just(buffer));
                            }
                        }
                    }
                }
            }//------------------>
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}

