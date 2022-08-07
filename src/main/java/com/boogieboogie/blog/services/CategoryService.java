package com.boogieboogie.blog.services;

import com.boogieboogie.blog.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {

    //create
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    //update
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer category_id);

    //delete
    void deleteCategory(Integer category_id);

    //get
    CategoryDTO getCategoryByCategoryID(Integer category_id);

    //getAll
    List<CategoryDTO> getAllCategory();
}
