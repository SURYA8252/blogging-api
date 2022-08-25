package com.bloggingapis.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bloggingapis.config.AppConstants;
import com.bloggingapis.entitys.Role;
import com.bloggingapis.entitys.User;
import com.bloggingapis.payloads.UserDto;
import com.bloggingapis.repositorys.RoleRepository;
import com.bloggingapis.repositorys.UserRepository;
import com.bloggingapis.services.UserService;
import com.bloggingapis.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User saveUser = this.userRepository.save(user);
		return this.userToUserDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, int id) {
		User user = this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","ID",id));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepository.save(user);
		UserDto userDto1 = this.userToUserDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(int id) {
		User user = this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "ID", id));
		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = this.userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUserById(int id) {
		User user = this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "ID", id));
        this.userRepository.delete(user);
	}
    private User dtoToUser(UserDto userDto) {
//    	User user = new User();
//    	user.setId(userDto.getId());
//    	user.setName(userDto.getName());
//    	user.setEmail(userDto.getEmail());
//    	user.setPassword(userDto.getPassword());
//    	user.setAbout(userDto.getAbout());
    	User user = this.modelMapper.map(userDto, User.class);
    	return user;
    }
    private UserDto userToUserDto(User user) {
//    	UserDto userDto = new UserDto();
//    	userDto.setId(user.getId());
//    	userDto.setName(user.getName());
//    	userDto.setEmail(user.getEmail());
//    	userDto.setPassword(user.getPassword());
//    	userDto.setAbout(user.getAbout());
    	UserDto userDto = this.modelMapper.map(user, UserDto.class);
    	return userDto;
    }

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepository.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}
}
