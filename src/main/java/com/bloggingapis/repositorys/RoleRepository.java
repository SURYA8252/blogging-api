package com.bloggingapis.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloggingapis.entitys.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
