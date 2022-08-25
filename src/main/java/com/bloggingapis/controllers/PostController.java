package com.bloggingapis.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bloggingapis.config.AppConstants;
import com.bloggingapis.payloads.ApisResponse;
import com.bloggingapis.payloads.PostDto;
import com.bloggingapis.payloads.PostResponse;
import com.bloggingapis.services.FileService;
import com.bloggingapis.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
    private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	//Create Post URI
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable("userId") int userId,@PathVariable("categoryId")int categoryId) {
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	//Get Post By UserId
	@GetMapping("/user/{id}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable int id) {
		List<PostDto> postsByUser = this.postService.getPostsByUser(id);
		return new ResponseEntity<List<PostDto>>(postsByUser,HttpStatus.OK);
	}
	//Get Post by Category Id
	@GetMapping("/category/{id}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable int id) {
	     List<PostDto> postsByCategory = this.postService.getPostsByCategory(id);
	     return new ResponseEntity<List<PostDto>>(postsByCategory,HttpStatus.OK);
	}
	//Get All Post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir) {
		PostResponse allPost = this.postService.getAllPost(pageNumber, pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
	}
	//Get Single Post
	@GetMapping("/post/{id}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable int id) {
		PostDto postDto = this.postService.getPostById(id);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	//Delete Post
	@DeleteMapping("/post/{id}")
	public ApisResponse deletePost(@PathVariable int id) {
		this.postService.deletePost(id);
		return new ApisResponse("Post Delete is Successfully.", true);
	}
	//Update Post
	@PutMapping("/post/{id}")
	public ResponseEntity<PostDto> updatePost(@PathVariable int id,@RequestBody PostDto postDto) {
		PostDto updatePost = this.postService.updatePost(postDto, id);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	//Search
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword) {
		List<PostDto> searchPosts = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.OK);
	}
	//Post Image Upload
	@PostMapping("/post/image/upload/{id}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile multipartFile,@PathVariable int id) throws IOException {
		PostDto postDto = this.postService.getPostById(id);
		String fileName = this.fileService.uploadImage(path, multipartFile);
		postDto.setImage(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, id);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	//Serve File
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
