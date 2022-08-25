package com.bloggingapis;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bloggingapis.config.AppConstants;
import com.bloggingapis.entitys.Role;
import com.bloggingapis.repositorys.RoleRepository;

@SpringBootApplication
public class BloggingApIsApplication implements CommandLineRunner{
	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(BloggingApIsApplication.class, args);
		System.out.println("Rest APIs for blogging Application");
	}
	@Bean
    public ModelMapper modelMapper() {
    	return new ModelMapper();
    }
	@Override
	public void run(String... args) throws Exception {
		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			List<Role> roles = List.of(role,role1);
			List<Role> saveAll = this.roleRepository.saveAll(roles);
			saveAll.forEach(s->{
				System.out.println(s.getName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
