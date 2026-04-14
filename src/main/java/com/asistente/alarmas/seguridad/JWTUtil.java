package com.asistente.alarmas.seguridad;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;
    private JwtParser parser;

    @PostConstruct
    void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.parser = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .setAllowedClockSkewSeconds(60)
                .build();
        log.info("JWTUtil inicializado");
    }

    public Claims extractClaims(String token) throws JwtException {
        return parser.parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims c = extractClaims(token);
            Date exp = c.getExpiration();
            boolean valid = (exp == null) || exp.after(new Date());
            if (!valid) log.warn("JWT expirado (exp={})", exp);
            return valid;
        } catch (ExpiredJwtException e) {
            log.warn("JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException | MalformedJwtException | SecurityException | IllegalArgumentException e) {
            log.warn("JWT inválido: {}", e.getMessage());
        } catch (JwtException e) {
            log.warn("Error procesando JWT: {}", e.getMessage());
        }
        return false;
    }
}
