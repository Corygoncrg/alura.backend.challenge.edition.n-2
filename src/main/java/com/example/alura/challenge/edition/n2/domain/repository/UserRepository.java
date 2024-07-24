package com.example.alura.challenge.edition.n2.domain.repository;

import com.example.alura.challenge.edition.n2.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin (String login);
}
