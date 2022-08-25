package com.bloggingapis.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloggingapis.entitys.Category;
import com.bloggingapis.exceptions.ResourceNotFoundException;
import com.bloggingapis.payloads.CategoryDto;
import com.bloggingapis.repositorys.CategoryRepository;
import com.bloggingapis.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category saveCategory = this.categoryRepository.save(category);
		return this.modelMapper.map(saveCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int id) {
		Category category = this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category ", " ID", id));
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		Category upadateCategory = this.categoryRepository.save(category);
		return this.modelMapper.map(upadateCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryById(int id) {
		Category category = this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", id));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> findAll = this.categoryRepository.findAll();
		List<CategoryDto> collect = findAll.stream().map((category)-> this.modelMapper.map(findAll, CategoryDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public void deleteCategoryById(int id) {
	    Category category = this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", id));
	    this.categoryRepository.delete(category);
    }

}
