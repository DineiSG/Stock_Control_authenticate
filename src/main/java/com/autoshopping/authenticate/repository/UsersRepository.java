package com.autoshopping.authenticate.repository;

import com.autoshopping.authenticate.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByLogin(String login);


    Optional<Users> findByNome(String nome);
}
