package com.bloggingapis.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloggingapis.payloads.ApisResponse;
import com.bloggingapis.payloads.UserDto;
import com.bloggingapis.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
	private UserService userService;
    //Post - Create User
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
    	UserDto createUserDto = this.userService.createUser(userDto);
    	return new ResponseEntity<UserDto>(createUserDto,HttpStatus.CREATED);
    }
    //PUT Update User
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("id") int id,@RequestBody UserDto userDto) {
    	UserDto updateUser = this.userService.updateUser(userDto, id);
    	return ResponseEntity.ok(updateUser);
    }
    //Delete User
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApisResponse> deleteUser(@PathVariable("id") int id) {
    	this.userService.deleteUserById(id);
    	return new ResponseEntity<ApisResponse>(new ApisResponse("User delete successfully", true),HttpStatus.OK);
    }
    //GET All User
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
    	return ResponseEntity.ok(this.userService.getAllUser());
    }
    //GET Get Singal User
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getSingalUser(@PathVariable int id) {
    	return ResponseEntity.ok(this.userService.getUserById(id));
    }
}
