package com.auth.oauthserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
@Slf4j
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

//        return http.formLogin(Customizer.withDefaults()).build();
        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient1 = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("first-party-id")
                .clientSecret(passwordEncoder.encode("first-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8088/login/oauth2/code/api-client-oidc")
                .redirectUri("http://127.0.0.1:8088/authorized")
                .scope(OidcScopes.OPENID)
                .scope("user")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .build();
        log.info("client1------------------>"+registeredClient1);
        RegisteredClient registeredClient2 = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("third-party-id")
                .clientSecret(passwordEncoder.encode("third-secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8088/authorized")
                .scope(OidcScopes.OPENID)
                .scope("third-party")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .build();
        log.info("client2------------>"+registeredClient2);


        return new InMemoryRegisteredClientRepository(registeredClient1,registeredClient2);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        log.info("--------------Source----------");
        RSAKey rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        log.info("-------------source-id-----------"+rsaKey+" "+jwkSet);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static RSAKey generateRsa() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        log.info("-----------generate--------"+keyPair);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        log.info("------------RSA----------"+privateKey);
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        log.info("-----------final key-----------------"+keyPair);
        return keyPair;
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                .issuer("http://LAPTOP-PLFS4SIV:9000")
                .build();
    }
}
