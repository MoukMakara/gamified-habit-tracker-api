package org.hrd.kps_group_01_spring_mini_project.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.checkerframework.checker.units.qual.A;
import org.hrd.kps_group_01_spring_mini_project.model.entity.AppUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Configuration
public class JwtUtils {

    private static final String SECRET = "4pssiwEhcK7IrlpzBrN6W3YhW/FioMhHyZrOXLrhsYOH77j9cMy1yGokOZKu+z9H";
    private static final Long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(100);


    //Generate Token
    public String generateToken(Map<String, Object> extraClaims, String appUserIdentifier) {

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(appUserIdentifier)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Generate Key to further Encrypt the BASE64 SECRET
    public SecretKey getSecretKey() {
        byte[] encodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(encodedKey);
    }

//Extract all information (claims) from token

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                //Verify Token with the sign in key provided
                .parseClaimsJws(token)
                //Get all information with getBody
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractIdentifier(String token) {
        return extractClaim(token, Claims::getSubject);
    }

//     retrieve expiration date from jwt token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


//     check expired token
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


//     check if token is valid
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = extractIdentifier(token);

        AppUser appUser = (AppUser) userDetails;

        boolean isValid = (email.equals(appUser.getUsername()) || email.equals(appUser.getEmail()));

        return (isValid && !isTokenExpired(token));
    }

}

