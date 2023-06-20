package com.auth.oauthserver.config;

import com.auth.oauthserver.filter.HashPasswordUtils;
import com.auth.oauthserver.filter.JwtUtil;
import com.auth.oauthserver.repository.UserRepository;
import com.auth.oauthserver.service.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@RequiredArgsConstructor
public class DefaultSecurityConfig {


    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final AuthenticationProvider authenticationProvider;
    private final HashPasswordUtils hashPasswordUtils;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

                http
                        .csrf().disable()
                        .authorizeHttpRequests().antMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .formLogin().disable();

                return http.build();
    }

    @Autowired
    public void bindAuthenticationProvider(AuthenticationManagerBuilder authenticationManagerBuilder) {

        authenticationManagerBuilder
                .authenticationProvider(customAuthenticationProvider);
    }
}
