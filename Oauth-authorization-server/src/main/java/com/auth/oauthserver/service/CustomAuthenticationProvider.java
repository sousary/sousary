package com.auth.oauthserver.service;

import com.auth.oauthserver.repository.UserRepository;
import com.auth.oauthserver.entity.User;
import com.auth.oauthserver.filter.HashPasswordUtils;
import com.auth.oauthserver.filter.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final HashPasswordUtils hashPasswordUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        log.info("---------------username--------------"+username);
        ;
        String password = authentication.getCredentials().toString();
        log.info("------------------pass---------------"+password);

        UserDetails user= customUserDetailsService.loadUserByUsername(username);
        log.info(String.valueOf(user));
        User users= userRepository.findUserByUsernameAndAppId(username,"FTBRMB");
        return checkPassword(user,password,users);
    }

    private Authentication checkPassword(UserDetails user, String rawPassword,User users) {
        String pSalt = users.getUserId()+ "FTBRETAILBANKING";
        log.info("------------------------------psalt-----------------------"+pSalt);

        String hashPassword = hashPasswordUtils.hashSHA256(rawPassword, pSalt);

        System.out.println("-------------------------hash password--------------"+hashPassword);
        System.out.println("user password========================="+users.getPassword()+" hash password================================="+hashPassword);
        if(Objects.equals(users.getPassword(), hashPassword)) {

           UsernamePasswordAuthenticationToken data= new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities());
            log.info("-----------------check-------------------"+data);
            return data;
        }
        else {
            throw new BadCredentialsException("Bad Credentials User");
        }
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
