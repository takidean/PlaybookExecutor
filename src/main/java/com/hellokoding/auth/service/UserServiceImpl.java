package com.hellokoding.auth.service;

import com.hellokoding.auth.model.Developper;
import com.hellokoding.auth.repository.RoleRepository;
import com.hellokoding.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Developper developper) {
        developper.setPassword(bCryptPasswordEncoder.encode(developper.getPassword()));
        developper.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(developper);
    }

    @Override
    public Developper findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
