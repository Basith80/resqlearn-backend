package com.sih.disasterplatform.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "SIH_DISASTER_SECRET_KEY";
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    public String generateToken(Long userId, String role, Long schoolId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("role", role)
                .claim("schoolId", schoolId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
