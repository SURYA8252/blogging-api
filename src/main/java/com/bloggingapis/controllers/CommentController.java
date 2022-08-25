package com.bloggingapis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloggingapis.payloads.ApisResponse;
import com.bloggingapis.payloads.CommentDto;
import com.bloggingapis.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	@Autowired
	private CommentService commentService;
	//Create Comment
	@PostMapping("/post/{id}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable int id,@RequestBody CommentDto commentDto) {
    	CommentDto createComment = this.commentService.createComment(commentDto, id);
    	return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
    }
	//Delete Comment
	@DeleteMapping("/comment/{id}")
	public ResponseEntity<ApisResponse> deleteComment(@PathVariable int id) {
		this.commentService.deleteComment(id);
		return new ResponseEntity<ApisResponse>(new ApisResponse("Comment delete Successfully.", true),HttpStatus.OK);
	}
}
