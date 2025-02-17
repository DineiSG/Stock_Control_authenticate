package com.autoshopping.authenticate.service;

import com.autoshopping.authenticate.repository.UsersRepository;
import com.autoshopping.authenticate.user.Users;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final UsersRepository usersRepository;


    public CustomUserDetailsService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users user= usersRepository.findByLogin(login)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário nao encontrado"));

        System.out.println("Buscando usuariio: " +login);
        System.out.println("Usuário encontrado: " + user.getLogin());
        System.out.println("Senha armazenada no banco: " + user.getSenha());

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getSenha(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
