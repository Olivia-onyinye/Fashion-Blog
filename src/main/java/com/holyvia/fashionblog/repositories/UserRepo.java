package com.holyvia.fashionblog.repositories;

import com.holyvia.fashionblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long > {
    boolean existsByEmail(String email);

    Optional<User> findUserByEmailAndPassword(String email, String password);
}
