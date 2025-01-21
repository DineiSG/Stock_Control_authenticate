package com.autoshopping.authenticate.service;

import com.autoshopping.authenticate.repository.UsersRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usersRepository.findByLogin(login)
                .map(user -> User.builder()
                        .username(user.getLogin())
                        .password(user.getSenha())
                        .build())
                .orElseThrow(()-> new UsernameNotFoundException("Usuário nao Encontrado"));
    }
}
