package com.bloggingapis.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloggingapis.entitys.Category;
import com.bloggingapis.entitys.Post;
import com.bloggingapis.entitys.User;

public interface PostRepository extends JpaRepository<Post, Integer>{
	//Custom method to get all user
    List<Post> findByUser(User user);
    //Custom method to get all category
    List<Post> findByCategory(Category category);
    //Search by title
    List<Post> findByTitleContaining(String title);
    //Search Query in JPQL
    //@Query("select p from Post p where p.title like :key")
    //List<Post> searchByTitle(@Param("key") String title);
}
