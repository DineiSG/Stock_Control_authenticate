package com.autoshopping.authenticate.repository;

import com.autoshopping.authenticate.user.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository <Users, String> {
    Optional<Users> findByLogin(String login);

}
