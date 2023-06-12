package com.auth.oauthserver.filter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.function.Function;
import com.nimbusds.jose.shaded.json.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class JwtUtil {

    private final RSAKeyUtils rsaKeyUtils;
    public static final String SECRET ="authTokenSecretKey";
    public static final String SHORTINTVL ="1800000";
    public static final String LONGINTVL ="1800000";

    public String getUsernameFromToken(String token) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("sub").toString();
    }

    public Date getExpirationDateFromToken(String token) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        PublicKey publicKey = rsaKeyUtils.getPublicKey();
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        return getExpirationDateFromToken(token).before(new Date());
    }



    public String finalToken(Map<String, Object> claims, Map<String, Object> accessTokenObj,String[] scope, String[] authArr) throws Exception {
        PrivateKey privateKey = rsaKeyUtils.getPrivateKey();

        Long validTime = Long.valueOf(LONGINTVL);
        return  Jwts.builder().setClaims(accessTokenObj)
                .setExpiration(new Date(Calendar.getInstance().getTimeInMillis()+validTime))
//				.claim("roles", roles)
                .claim("auth",authArr)
                .claim("scp",scope)
                .signWith(SignatureAlgorithm.RS256, privateKey).compact();
    }
    public String lastToken() throws Exception {
        PrivateKey privateKey = rsaKeyUtils.getPrivateKey();

        Map<String, Object> claims = new HashMap<>();
        Map<String, Object> accessTokenObj = new HashMap<>();
        Map<String, Object> dataObj = new HashMap<>();
        String[] authArr = {"wing_transfer","lyhour_transfer"};
        String[] scp = {"read","write","view"};
        JSONObject token= new JSONObject();

        accessTokenObj.put("auth", authArr);
        accessTokenObj.put("grant", "password");
        accessTokenObj.put("state", UUID.randomUUID().toString());
        accessTokenObj.put("cid","first-party-client");
        accessTokenObj.put("exp", Calendar.getInstance().getTimeInMillis());
        log.info("-------------claim-----------"+accessTokenObj);

        Long validTime = Long.valueOf(LONGINTVL);
        String accessToken=  Jwts.builder().setClaims(accessTokenObj)
                .setExpiration(new Date(Calendar.getInstance().getTimeInMillis()+validTime))
                .claim("auth",authArr)
                .claim("scp",scp)
                .signWith(SignatureAlgorithm.RS256, privateKey).compact();
        log.info("-----------------generate final token---------------: "+accessToken);
        return accessToken;
    }


    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}

