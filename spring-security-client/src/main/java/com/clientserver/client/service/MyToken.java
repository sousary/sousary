package com.clientserver.client.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyToken {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Autowired
    public MyToken(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    public String getAccessToken(Authentication authentication, String clientRegistrationId) {
        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(
                OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationId)
                        .principal(authentication)
                        .build()
        );
        OAuth2AccessToken access = authorizedClient.getAccessToken();
        log.info("--------------------------------------> access token======="+access);
        return access.getTokenValue();




//    public String getAccessToken(Authentication authentication, String clientRegistrationId) {
//        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(
//                OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationId)
//                        .principal(authentication)
//                        .build()
//        );
//
//        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();


    }
}

