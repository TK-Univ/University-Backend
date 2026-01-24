package com.springboot.university.common.jwt;

import com.springboot.university.domain.staff.StaffAuthority;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expiration) {
        // HMAC-SHA 알고리즘을 위한 키 생성 (문자열을 바이트 배열로 변환)
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String createToken(String userId, String authority) {
        return Jwts.builder()
                .subject(userId)
                .claim("auth", authority)
                .signWith(secretKey)
                .compact();
    }
}
