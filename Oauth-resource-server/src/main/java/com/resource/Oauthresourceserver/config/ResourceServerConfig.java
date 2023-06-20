package com.resource.Oauthresourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/**").access("hasIpAddress('127.0.0.1')")
                .antMatchers("/api/users")
                .access("hasAnyAuthority('SCOPE_user')")
                .anyRequest().authenticated()
//                .and()
//                .cors()
//                .configurationSource(corsConfigurationSource())
//                .and().portMapper().http(8090).mapsTo(9227)
//                .access("hasAnyAuthority('SCOPE_api.read','SCOPE_api.write','SCOPE_user')")

//                .hasAnyAuthority("SCOPE_api.read","SCOPE_api.write","SCOPE_user")
//                .antMatchers("/api/test")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();



    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:9227"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization"));
//        configuration.setAllowCredentials(false);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}

