package com.myhotels.authorizationserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {


    private static String secret;
    private static Key signingKey;

    @PostConstruct
    public static void init() {
        signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

    public static String createJwt(Map<String, String> payload, long expMillis) {

        long nowMillis = System.currentTimeMillis();

        return Jwts.builder()
                .setIssuedAt(new Date(nowMillis))
//                .setPayload(payload)
                .setClaims(payload)
                .setExpiration(new Date(nowMillis + expMillis))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

}
