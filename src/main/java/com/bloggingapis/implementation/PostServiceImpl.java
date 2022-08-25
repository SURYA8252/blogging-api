package com.bloggingapis.implementation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bloggingapis.entitys.Category;
import com.bloggingapis.entitys.Post;
import com.bloggingapis.entitys.User;
import com.bloggingapis.exceptions.ResourceNotFoundException;
import com.bloggingapis.payloads.PostDto;
import com.bloggingapis.payloads.PostResponse;
import com.bloggingapis.repositorys.CategoryRepository;
import com.bloggingapis.repositorys.PostRepository;
import com.bloggingapis.repositorys.UserRepository;
import com.bloggingapis.services.PostService;

@Service
public class PostServiceImpl implements PostService {
    
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto,int userId,int categoryId) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "ID", userId));
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImage("default.png");
		post.setDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post save = this.postRepository.save(post);
		return this.modelMapper.map(save, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int id) {
		Post post = this.postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "ID", id));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImage(postDto.getImage());
		Post updatedPost = this.postRepository.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(int id) {
		Post post = this.postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "ID", id));
		this.postRepository.delete(post);
	}

	@Override
	public PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir) {
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = this.postRepository.findAll(pageable);
		List<Post> findAll = page.getContent();
		List<PostDto> postDtos = findAll.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(page.getNumber());
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElements(page.getTotalElements());
		postResponse.setTotalPages(page.getTotalPages());
		postResponse.setLastPage(page.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(int id) {
		Post post = this.postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "ID", id));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(int id) {
		Category category = this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", id));
		List<Post> posts = this.postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(int id) {
		User user = this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "ID", id));
		List<Post> posts = this.postRepository.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepository.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
