package com.autoshopping.authenticate.controller;


import com.autoshopping.authenticate.service.UsersService;
import com.autoshopping.authenticate.token.JwtTokenProvider;
import com.autoshopping.authenticate.user.Users;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsersController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UsersController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider){
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    private UsersService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<Iterable<Users>> get(){
        return ResponseEntity.ok(service.getUsers());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity get(@PathVariable("nome") String nome){
        Optional<Users> usuarios = service.getUsersByNome(nome);
        return usuarios
                .map(Users -> ResponseEntity.ok(usuarios))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity <String>post(@RequestBody Users user){
        
        String hashedPassword = passwordEncoder.encode(user.getSenha());
        user.setSenha(hashedPassword);
        
        Users users=service.insert(user);
        return ResponseEntity.ok("Cadastro de usuario realizado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
       try{
           Authentication authenticationRequest =
                   new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.senha());

           Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

           String jwtToken = jwtTokenProvider.generateToken(authenticationResponse);

           return ResponseEntity.ok(new JwtResponse(jwtToken));
       }catch (BadCredentialsException e){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    public record LoginRequest(String login, String senha){}

    public record JwtResponse(String token){};






}
