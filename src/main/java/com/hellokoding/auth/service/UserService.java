package com.hellokoding.auth.service;

import com.hellokoding.auth.model.Developper;

public interface UserService {
    void save(Developper developper);

    Developper findByUsername(String username);
}
