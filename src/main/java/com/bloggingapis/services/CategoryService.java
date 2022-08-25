package com.bloggingapis.services;

import java.util.List;

import com.bloggingapis.payloads.CategoryDto;

public interface CategoryService {
	//Create Category
    CategoryDto createCategory(CategoryDto categoryDto);
    //Update Category
    CategoryDto updateCategory(CategoryDto categoryDto,int id);
    //Get Singal Category
    CategoryDto getCategoryById(int id);
    //All Category
    List<CategoryDto> getAllCategory();
    //Delete CategoryById
    void deleteCategoryById(int id);
}
