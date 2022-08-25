package com.bloggingapis.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloggingapis.entitys.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
}
