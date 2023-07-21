package com.example.OauthGatewayJwt.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {
    @Value("${public.key.path}")//------------------>local location
    private String PUBLIC_KEY_PATH;
    public RSAPublicKey getPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(PUBLIC_KEY_PATH));
        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(spec);
    }
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
        PublicKey publicKey = getPublicKey();
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






