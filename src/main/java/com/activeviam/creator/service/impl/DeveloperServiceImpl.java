package com.activeviam.creator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activeviam.creator.repository.DevelopperRepository;
import com.activeviam.creator.service.DeveloperService;

@Service
public class DeveloperServiceImpl implements DeveloperService{

	@Autowired
	DevelopperRepository developperRepository;
	@Override
	public boolean validateDeveloper(String username) {
 			return developperRepository.findByUsername(username) !=null ? true : false;
	}
	
}
