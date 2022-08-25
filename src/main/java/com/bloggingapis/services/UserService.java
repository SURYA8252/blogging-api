package com.bloggingapis.services;

import java.util.List;

import com.bloggingapis.payloads.UserDto;

public interface UserService {
	//Create register
	UserDto registerNewUser(UserDto userDto);
	//Save User
    UserDto createUser(UserDto userDto);
    //Update User
    UserDto updateUser(UserDto userDto,int id);
    //Get Singal User
    UserDto getUserById(int id);
    //All User
    List<UserDto> getAllUser();
    //Delete User By Id
    void deleteUserById(int id);
} 
