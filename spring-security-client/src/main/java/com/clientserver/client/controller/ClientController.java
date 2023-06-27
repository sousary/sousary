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
public class ClientController {
    private final WebClient webClient;

    public ClientController(WebClient webClient) {
        this.webClient = webClient;
    }


    @GetMapping("/hello") //-------->public not need access token
    public String hello(Principal principal) {
        return "Hello " +principal.getName()+", get hello from free access token";
    }
    @GetMapping(value = "/api/users")
    public String users(@RegisteredOAuth2AuthorizedClient("api-client-authorization-code") OAuth2AuthorizedClient client)
    {
        log.info("into controller---------------------------------->");
        String stringReturn = this.webClient
                .get()
                .uri("http://127.0.0.1:9001/api/users")//----------------------->with gateway port
                .attributes(oauth2AuthorizedClient(client))
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToMono(String  .class)
                .block();
        log.info("-----------------String response---------------------->"+stringReturn);
        return  stringReturn;

    }

}
