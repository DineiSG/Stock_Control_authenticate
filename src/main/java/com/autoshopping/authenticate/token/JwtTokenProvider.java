package com.autoshopping.authenticate.token;

import com.autoshopping.authenticate.repository.UsersRepository;
import com.autoshopping.authenticate.user.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String jwtSecret="UMvQyW3PxAOUtCTpuorS46Isa+Jjjvb2iAdLUzhCu0SmmGIo/MrYw6KsPOusfylPFLJE2/7EUf21Ixmnp+7IxQ==";
    private final long jwtExpirationMs = 86400000;

    public String generateToken (Authentication authentication, UsersRepository usersRepository){
        String username = authentication.getName();
        Users user = usersRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));

        return Jwts.builder()
                .setSubject(username)
                .claim("role", user.getTipo())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
