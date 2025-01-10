package com.todo.config;

import com.todo.models.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${SECRET}")
    private String jwtSecret;

    @Value("${JWT_TIME}")
    private long jwtExpirationMs;

    public String generateTokenFromUsername(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expirationDate = parseClaims(token).getExpiration();
            return expirationDate.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public boolean isRefreshTokenExpired(RefreshToken token) {
        return token.getExpirationTimestamp().isBefore(LocalDateTime.now());
    }

    public String refreshAccessToken(String currentToken, String username) {
        if (isTokenExpired(currentToken)) {
            return generateTokenFromUsername(username, parseClaims(currentToken));
        }
        return currentToken;
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSignInKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired: " + e.getMessage());
            return e.getClaims();
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
}
