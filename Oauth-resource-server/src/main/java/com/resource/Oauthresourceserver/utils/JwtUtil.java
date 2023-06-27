package com.resource.Oauthresourceserver.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.PublicKey;
import java.util.Date;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtUtil {
    private final RSAKeyUtils rsaKeyUtils;

    public static final String SECRET ="authTokenSecretKey";
    public static final String SHORTINTVL ="1800000";
    public static final String LONGINTVL ="1800000";

    public String getUsernameFromToken(String token) throws Exception {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("sub").toString();
    }

    public Date getExpirationDateFromToken(String token) throws Exception {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws Exception {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) throws Exception {

        //Get Private Key for decrypt
        PublicKey publicKey = rsaKeyUtils.getPublicKey();
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) throws Exception {
        return getExpirationDateFromToken(token).before(new Date());
    }


    //validate token
    public Boolean validateToken(String token) throws Exception {
        final String username = getUsernameFromToken(token);
        Boolean isTokenValid = username != null && !isTokenExpired(token);
        return isTokenValid;
    }



}

