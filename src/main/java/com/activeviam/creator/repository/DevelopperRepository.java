package com.activeviam.creator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.activeviam.creator.model.Developper;

public interface DevelopperRepository extends JpaRepository<Developper, Long> {
    Developper findByUsername(String username);
}
