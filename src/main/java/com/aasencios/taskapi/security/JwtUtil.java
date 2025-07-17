package com.aasencios.taskapi.security;

import com.aasencios.taskapi.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "mi_clave_secreta_robusta_y_segura_123456";

    private final long EXPIRATION_TIME = 86400; // 24 hours

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ✅ Token con rol
    public String generateToken(String email, Role role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.name()) // Agregamos el rol
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Extraer email
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ Extraer rol
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token, String userEmail) {
        String email = extractEmail(token);
        return email.equals(userEmail) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
