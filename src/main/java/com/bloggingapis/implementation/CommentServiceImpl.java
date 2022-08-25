package com.bloggingapis.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloggingapis.entitys.Comment;
import com.bloggingapis.entitys.Post;
import com.bloggingapis.exceptions.ResourceNotFoundException;
import com.bloggingapis.payloads.CommentDto;
import com.bloggingapis.repositorys.CommentRepository;
import com.bloggingapis.repositorys.PostRepository;
import com.bloggingapis.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
    
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, int id) {
		Post post = this.postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "ID", id));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepository.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(int id) {
		Comment comment = this.commentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment", "ID", id));
		this.commentRepository.delete(comment);
	}

}
