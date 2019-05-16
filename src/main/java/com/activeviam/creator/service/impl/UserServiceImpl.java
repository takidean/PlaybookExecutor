package com.activeviam.creator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.activeviam.creator.model.Developper;
import com.activeviam.creator.repository.RoleRepository;
import com.activeviam.creator.repository.UserRepository;
import com.activeviam.creator.service.UserService;

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
