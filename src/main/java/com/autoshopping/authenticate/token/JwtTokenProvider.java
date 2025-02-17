package com.autoshopping.authenticate.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String jwtSecret="UMvQyW3PxAOUtCTpuorS46Isa+Jjjvb2iAdLUzhCu0SmmGIo/MrYw6KsPOusfylPFLJE2/7EUf21Ixmnp+7IxQ==";
    private final long jwtExpirationMs = 86400000;

    public String generateToken (Authentication authentication){
        String username = authentication.getName();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
