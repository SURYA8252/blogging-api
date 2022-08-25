package com.bloggingapis.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloggingapis.payloads.ApisResponse;
import com.bloggingapis.payloads.CategoryDto;
import com.bloggingapis.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
    private CategoryService categoryService;
	//Create Category URI
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	//Update Category URI
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable int id) {
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, id);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	}
	//Get Singal Category URI
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getSingalCategory(@PathVariable int id) {
		CategoryDto categoryById = this.categoryService.getCategoryById(id);
		return new ResponseEntity<CategoryDto>(categoryById,HttpStatus.OK);
	}
	//Get All category URI
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
	    List<CategoryDto> allCategory = this.categoryService.getAllCategory();
	    return new ResponseEntity<List<CategoryDto>>(allCategory,HttpStatus.OK);
	}
	//Delete Category URI
	@DeleteMapping("/{id}")
	public ResponseEntity<ApisResponse> deleteCategory(@PathVariable int id) {
		this.categoryService.deleteCategoryById(id);
		return new ResponseEntity<ApisResponse>(new ApisResponse("Category is delete successfully !!", true),HttpStatus.OK);
	}
}
