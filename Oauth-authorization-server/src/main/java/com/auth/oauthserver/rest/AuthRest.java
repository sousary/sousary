package com.auth.oauthserver.rest;
import com.auth.oauthserver.filter.JwtUtil;
import com.auth.oauthserver.payload.AuthenticationRequest;
import com.auth.oauthserver.service.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthRest {
    private final JwtUtil jwtUtil;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    @PostMapping("/login")
    public String processLoginForm(@RequestBody AuthenticationRequest req, HttpServletRequest request) {
        String username = req.getUsername();
        String password = req.getPassword();
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticated = customAuthenticationProvider.authenticate(authentication);

            SecurityContextHolder.getContext().setAuthentication(authenticated);

            return ResponseEntity.ok().toString();
        } catch (AuthenticationException e) {
            return "Not success Authenticated!!";
        }
    }
    ///////////////////////////not work //////////////////////////////////////

}


