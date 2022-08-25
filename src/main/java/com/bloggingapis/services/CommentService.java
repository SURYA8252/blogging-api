package com.bloggingapis.services;

import com.bloggingapis.payloads.CommentDto;

public interface CommentService {
    //Create Comment
	CommentDto createComment(CommentDto commentDto,int id);
    //Delete Comment
	void deleteComment(int id);
}
