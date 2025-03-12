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

    private final String jwtSecret="V/efm5bAK8N4t1bxu5NeoDdPIsWwLxBx6TqGobOoCs6Ebr8duDK2d3a/5u9dIdvJYEalmznwoIhmAOFvbH0Tcw==";
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
