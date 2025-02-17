package com.autoshopping.authenticate.service;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    //Gera um token JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, new SecretKeySpec(secretKey.getBytes(), "HmacSHA256"))
                .compact();
    }

    //Valida se o token é válido
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256"))
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Token nao suportado: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Token invalido: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Token vazio ou inválido: " + e.getMessage());
        }
        return false;
    }

    //Extrai os claims do token
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256"))
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }


}
