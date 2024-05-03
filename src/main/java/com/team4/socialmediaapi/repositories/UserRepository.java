package com.team4.socialmediaapi.repositories;

import com.team4.socialmediaapi.entities.Credentials;
import com.team4.socialmediaapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCredentials(Credentials credentials);
    
    Optional<User> findByCredentials_Username(String username);
    Optional<User> findByCredentialsUsernameAndDeletedFalse(String string);

    User findByCredentialsUsername(String username);
    User findByCredentialsUsernameAndCredentialsPassword(String username, String password);

    List<User> findAllByDeletedFalse();

}
