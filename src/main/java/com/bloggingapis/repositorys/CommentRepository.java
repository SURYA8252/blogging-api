package com.bloggingapis.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloggingapis.entitys.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
