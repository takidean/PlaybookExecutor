package com.hellokoding.auth.repository;

import com.hellokoding.auth.model.Developper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Developper, Long> {
    Developper findByUsername(String username);
}
