package com.autoshopping.authenticate.controller;


import com.autoshopping.authenticate.repository.UsersRepository;
import com.autoshopping.authenticate.service.JwtService;
import com.autoshopping.authenticate.service.UsersService;
import com.autoshopping.authenticate.token.JwtTokenProvider;
import com.autoshopping.authenticate.user.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsersRepository usersRepository;

    public UsersController(JwtService jwtService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider){
        this.jwtService = jwtService;
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

    //Criar usuario
    @PostMapping("/create-user")
    public ResponseEntity<String> post(@RequestBody Users user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        user.setLogin(user.getLogin());
        user.setSenha(encoder.encode(user.getSenha())); // Criptografa a senha corretamente
        usersRepository.save(user);

        return ResponseEntity.ok("Usuário criado com sucesso!");
    }

    //Logar usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        logger.info("Login realizado pelo usuario {}.", loginRequest);

        Users user = usersRepository.findByLogin(loginRequest.login())
                .orElseThrow(()-> new UsernameNotFoundException("Usuario nao encontrado."));

        System.out.println("Tentando login para: "+loginRequest.login());
        System.out.println("Senha digitada: "+loginRequest.senha());
        System.out.println("Senha armazenada: "+ user.getSenha());

        if(!passwordEncoder.matches(loginRequest.senha(), user.getSenha())){
            System.out.println(" SENHA INCORRETA !");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("SENHA INCORRETA !");
        }
        Authentication authenticationRequest =
                new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.senha());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
        String jwtToken = jwtTokenProvider.generateToken(authenticationResponse, usersRepository);
        System.out.println("Token gerado: " +jwtToken);
        return ResponseEntity.ok(new JwtResponse(jwtToken));


    }

    public record LoginRequest(String login, String senha){}

    public record JwtResponse(String token){};






}
