package com.activeviam.creator.service;

import com.activeviam.creator.model.Developper;

public interface UserService {
    void save(Developper developper);

    Developper findByUsername(String username);
}
