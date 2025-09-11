package com.week4.prod_ready_features.prod_ready_features.services.Auth;



import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret_key}")
    private String secretKey;


    private SecretKey getSecretKey(){
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    public String generateAccessToken(UserEntity userEntity){
        return Jwts.builder()
                .setSubject(userEntity.getId().toString())
                .claim("email", userEntity.getEmail())
                .claim("role", "USER")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(UserEntity userEntity){
        return Jwts.builder()
                .setSubject(userEntity.getId().toString())
                .claim("email", userEntity.getEmail())
                .claim("role", "USER")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 12))
                .signWith(getSecretKey())
                .compact();
    }


    public Long getUserIdFromToken(String token){
        Claims claims =  Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.parseLong(claims.getSubject());
    }
}
