package com.bloggingapis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloggingapis.config.JwtTokenHelper;
import com.bloggingapis.exceptions.ApiException;
import com.bloggingapis.payloads.JwtAuthRequest;
import com.bloggingapis.payloads.JwtAuthResponse;
import com.bloggingapis.payloads.UserDto;
import com.bloggingapis.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	@PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {
    	this.authenticate(jwtAuthRequest.getEmail(),jwtAuthRequest.getPassword());
    	UserDetails loadUserByUsername = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());
    	String generateToken = this.jwtTokenHelper.generateToken(loadUserByUsername);
    	JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
    	jwtAuthResponse.setToken(generateToken);
    	return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);
    }
	private void authenticate(String email, String password) throws Exception {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email,password);
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details !!");
			throw new ApiException("Invalid Username & password !!");
		}
	}
	//Register User
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
		UserDto registerNewUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registerNewUser,HttpStatus.CREATED);
	}
}
