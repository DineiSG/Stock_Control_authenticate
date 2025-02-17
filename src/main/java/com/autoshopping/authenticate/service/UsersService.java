package com.autoshopping.authenticate.service;

import com.autoshopping.authenticate.repository.UsersRepository;
import com.autoshopping.authenticate.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Iterable<Users> getUsers() {
        return repository.findAll();
    }

    public Optional<Users> getUsersByNome(String nome) {
        return repository.findByNome(nome);
    }

    public Users insert(Users user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return repository.save(user);
    }

    public Optional<Users> getUsersByLogin(String login) { return repository.findByLogin(login);
    }
}
