package com.bloggingapis.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloggingapis.entitys.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
