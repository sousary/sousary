package com.clientserver.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import java.security.Principal;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;


@RestController
@Slf4j
@RequestMapping("/api")
public class ClientController {


    private final WebClient webClient;

    public ClientController(WebClient webClient) {
        this.webClient = webClient;
    }


    @GetMapping("/hello")
    public String hello(Principal principal) {
        return "Hello " +principal.getName()+", get hello from free access token";
    }
    @GetMapping(value = "/users")
    public String users(@RegisteredOAuth2AuthorizedClient("api-client-authorization-code") OAuth2AuthorizedClient client1,
                        @RegisteredOAuth2AuthorizedClient("third-client-authorization-code") OAuth2AuthorizedClient client2
                        )
    {
        log.info("into controller---------------------------------->");
        String stringReturn = this.webClient
                .get()
                .uri("http://127.0.0.1:8090/api/users")
                .attributes(oauth2AuthorizedClient(client1))
                .attributes(oauth2AuthorizedClient(client2))
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String  .class)
                .block();
        log.info("-----------------String response---------------------->"+stringReturn);
        return  stringReturn;

    }
    @GetMapping(value = "/free")
    public String[] free(@RegisteredOAuth2AuthorizedClient("api-client-authorization-code") OAuth2AuthorizedClient client1,
                        @RegisteredOAuth2AuthorizedClient("third-client-authorization-code") OAuth2AuthorizedClient client2
    )
    {
        log.info("into controller---------------------------------->");
        String[] stringArray = this.webClient
                .get()
                .uri("http://127.0.0.1:8090/api/free")
                .attributes(oauth2AuthorizedClient(client1))
                .attributes(oauth2AuthorizedClient(client2))
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String[] .class)
                .block();
        log.info("-----------------String response---------------------->"+stringArray);
        return  stringArray;

    }
    @GetMapping(value = "/test")
    public String[] test(@RegisteredOAuth2AuthorizedClient("api-client-authorization-code") OAuth2AuthorizedClient client1,
                         @RegisteredOAuth2AuthorizedClient("third-client-authorization-code") OAuth2AuthorizedClient client2
    )
    {
        log.info("into controller---------------------------------->");
        String[] string = this.webClient
                .get()
                .uri("http://localhost:9001/api/test")
                .attributes(oauth2AuthorizedClient(client1))
                .attributes(oauth2AuthorizedClient(client2))
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String[] .class)
                .block();
        log.info("-----------------String response---------------------->"+string);
        return  string;

    }
}
