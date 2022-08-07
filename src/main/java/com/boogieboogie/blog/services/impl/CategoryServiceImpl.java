package com.boogieboogie.blog.services.impl;

import com.boogieboogie.blog.exceptions.ResourseNotFoundException;
import com.boogieboogie.blog.model.Category;
import com.boogieboogie.blog.payload.CategoryDTO;
import com.boogieboogie.blog.repos.CategoryRepo;
import com.boogieboogie.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepo categoryRepo;
    
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category= this.dtoToCategory(categoryDTO);
        Category savedCategory = this.categoryRepo.save(category);
        return this.categoryToDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer category_id) {
        Category category = this.categoryRepo.findById(category_id).orElseThrow(() -> new ResourseNotFoundException("Category", "id", category_id));
        category.setCategory_title(categoryDTO.getCategory_title());
        category.setCategory_description(categoryDTO.getCategory_description());
        Category savedCat = this.categoryRepo.save(category);
        return this.categoryToDTO(savedCat);
    }

    @Override
    public void deleteCategory(Integer category_id) {
        Category category = this.categoryRepo.findById(category_id).orElseThrow(() -> new ResourseNotFoundException("Category", "id", category_id));
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDTO getCategoryByCategoryID(Integer category_id) {
        Category category = this.categoryRepo.findById(category_id).orElseThrow(() -> new ResourseNotFoundException("Category", "id", category_id));
        return this.categoryToDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category>res= this.categoryRepo.findAll();
        return res.stream().map((cat) -> this.categoryToDTO(cat)).collect(Collectors.toList());
    }
    
    @Autowired
    private ModelMapper modelMapper;
    
    private CategoryDTO categoryToDTO(Category category){
        return this.modelMapper.map(category,CategoryDTO.class);
    }
    
    private Category dtoToCategory(CategoryDTO categoryDTO){
        return this.modelMapper.map(categoryDTO, Category.class);
    }
}
