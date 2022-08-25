package com.bloggingapis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bloggingapis.entitys.User;
import com.bloggingapis.exceptions.ResourceNotFoundException;
import com.bloggingapis.repositorys.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
    @Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Loading user from database by username
		User user = this.userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "Email "+username, 0));
		return user;
	}

}
