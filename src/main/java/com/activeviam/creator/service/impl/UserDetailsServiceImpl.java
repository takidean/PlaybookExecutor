package com.activeviam.creator.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	/**TODO**/
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
    	//Not implemented Yet
    	return null;
    }



}
