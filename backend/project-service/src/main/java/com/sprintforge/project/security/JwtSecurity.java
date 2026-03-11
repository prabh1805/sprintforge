package com.sprintforge.project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtSecurity {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long extractUserId(String token){
        return extractAllClaims(token).get("userId", Long.class);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
    public Boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new java.util.Date());
    }
    public Boolean isTokenValid(String token){
        try{
            if(isTokenExpired(token)){
                return false;
            }
            extractAllClaims(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
