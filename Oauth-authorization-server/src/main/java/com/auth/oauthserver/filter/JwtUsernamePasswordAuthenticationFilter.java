package com.auth.oauthserver.filter;

import com.auth.oauthserver.repository.UserRepository;
import com.auth.oauthserver.entity.User;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationProvider authenticationManager;

    private final HashPasswordUtils hashPasswordUtils;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            System.out.println("User Attempt to Login");
//            AuthenticationRequest authRequest = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequest.class);
//            log.info("------------------log---------------------");
//
//
//            String username = authRequest.getUsername();
//            String password = authRequest.getPassword();
//
//            System.out.println(username + " " + password );
//            User user = userRepository.findUserByUsernameAndAppId(authRequest.getUsername(), "FTBRMB");
//
//
//            String pSalt = user.getUserId() + "FTBRETAILBANKING";
//            String hashPassword = hashPasswordUtils.hashSHA256(authRequest.getPassword(), pSalt);
//
//            logger.info("Trying to authenticate user");;
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    authRequest.getUsername(),
//                    hashPassword
//            );
//            log.info("-----------authenticate--------------"+authentication);
//
//            Authentication authenticate = authenticationManager.authenticate(authentication);
//            System.out.println("authenticate : " +  authenticate.isAuthenticated());
//            return authenticate;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("Successfully Login ! Username " + authResult.getName());
        User user = userRepository.findUserByUsernameAndAppId(authResult.getName(), "FTBRMB");

        Map<String, Object> claims = new HashMap<>();
        Map<String, Object> accessTokenObj = new HashMap<>();
        Map<String, Object> dataObj = new HashMap<>();
        String[] authArr = {"wing_transfer","lyhour_transfer"};
        String[] scp = {"read","write","view"};
        JSONObject token= new JSONObject();

        accessTokenObj.put("auth", authArr);
        accessTokenObj.put("last",user.getLastname());
        accessTokenObj.put("sub", user.getUsername());
        accessTokenObj.put("grant", "password");
        accessTokenObj.put("state", UUID.randomUUID().toString());
        accessTokenObj.put("cid","first-party-client");
        accessTokenObj.put("exp", Calendar.getInstance().getTimeInMillis());
        log.info("-------------claim-----------"+accessTokenObj);

        final String finalToken;
        try {
            finalToken = jwtUtil.finalToken(claims,accessTokenObj,scp, authArr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        String accessToken = jwtUtil.generateToken(user);

        System.out.println("User Successfully Login");

        response.setHeader("Authorization", "Bearer " + finalToken);
        response.getOutputStream().print("Successfully Login");
    }


}
