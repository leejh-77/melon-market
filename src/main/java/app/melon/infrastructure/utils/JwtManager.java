package app.melon.infrastructure.utils;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;

@Component
public class JwtManager {

    private final Key secretKey;

    public JwtManager(@Value("${app.jwt.secret_key}") String salt) {
        this.secretKey = Keys.hmacShaKeyFor(salt.getBytes());
    }

    public String generate(long id) {
        return Jwts.builder().setSubject(String.valueOf(id)).signWith(this.secretKey).compact();
    }

    public long extractId(String jwt) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build();
        String sub = parser.parseClaimsJws(jwt).getBody().getSubject();
        return Long.parseLong(sub);
    }
}
