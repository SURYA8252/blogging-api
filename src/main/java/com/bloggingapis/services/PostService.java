package com.bloggingapis.services;

import java.util.List;

import com.bloggingapis.payloads.PostDto;
import com.bloggingapis.payloads.PostResponse;

public interface PostService {
    //Create Post
	PostDto createPost(PostDto postDto,int userId,int categoryId);
	//Update Post
	PostDto updatePost(PostDto postDto,int id);
	//Delete Post
	void deletePost(int id);
	//Get All Post
	PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir);
	//Get single post
	PostDto getPostById(int id);
	//Get All post by category
	List<PostDto> getPostsByCategory(int id);
	//Get All post by user
	List<PostDto> getPostsByUser(int id);
	//Search Posts
	List<PostDto> searchPosts(String keyword);
}
