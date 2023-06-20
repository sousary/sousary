package com.clientserver.client.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
@Slf4j
public class WebClientConfiguration {
    @Bean
    WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        log.info("---------------------------------search web client-----------------"+ oauth2Client);
        return WebClient.builder()
                .apply(oauth2Client.oauth2Configuration())
                .build();
    }
    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .build();
        log.info("----------------------before move to client-----------------------------"+ authorizedClientRepository);
        DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        log.info("------------------webclientmanager----------->"+authorizedClientManager.toString());

        return authorizedClientManager;
    }
//    @Bean // with spring-boot-starter-web
//    WebClient webClient(
//            ClientRegistrationRepository clientRegistrationRepository,
//            OAuth2AuthorizedClientService authorizedClientService
//    ) {
//        var oauth = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
//                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
//                        clientRegistrationRepository, authorizedClientService
//                )
//        );
//        oauth.setDefaultClientRegistrationId("first-party-id");
//        return WebClient.builder()
//                .apply(oauth.oauth2Configuration())
//                .build();
//    }
//@Bean
//WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
//    ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
//            new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
//    log.info("---------------------------------search web client-----------------"+ oauth2Client);
//
//    WebClient.Builder builder = WebClient.builder()
//            .apply(oauth2Client.oauth2Configuration());
//
//    return builder.build();
//}
//
//    public ResponseEntity<?> getToken(Principal principal, WebClient webClient) {
//        String tokenEndpointUrl = "http://oauth-server-url/oauth/token"; // Replace with the actual token endpoint URL
//
//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("client_id", "first-party-id");
//        requestBody.add("client_secret", "first-secret");
//        requestBody.add("grant_type", "password");
//        requestBody.add("password", user.getPassword() );
//        requestBody.add("scope", "api.read");
//        requestBody.add("username", user.getUsername());
//
//        ResponseEntity<?> response = webClient.post()
//                .uri(tokenEndpointUrl)
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .bodyValue(requestBody)
//                .retrieve()
//                .toEntity(Object.class)
//                .block();
//
//        return response;
//    }


    //    @Bean
//    OAuth2AuthorizedClientManager authorizedClientManager
//            (ClientRegistrationRepository clients) {
//        OAuth2AuthorizedClientService service =
//                new InMemoryOAuth2AuthorizedClientService(clients);
//        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
//                new AuthorizedClientServiceOAuth2AuthorizedClientManager(clients, service);
//        OAuth2AuthorizedClientProvider authorizedClientProvider =
//                OAuth2AuthorizedClientProviderBuilder.builder()
//                        .clientCredentials()
//                        .build();
//        manager.setAuthorizedClientProvider(authorizedClientProvider);
//        log.info("Access token----------------------------------->"+manager);
//        return manager;
//    }



}




