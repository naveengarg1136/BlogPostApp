package com.boogieboogie.blog.controllers;

import com.boogieboogie.blog.payload.CategoryDTO;
import com.boogieboogie.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedcategory = this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedcategory, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{category_id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer category_id){
        CategoryDTO categoryDTO1 = this.categoryService.updateCategory(categoryDTO, category_id);
        return new ResponseEntity<>(categoryDTO1, HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{category_id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer category_id){
        this.categoryService.deleteCategory(category_id);
        return new ResponseEntity<>(Map.of("message", "Category deleted successfully"),HttpStatus.OK);
    }
    //get
    @GetMapping("/{category_id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer category_id){
        CategoryDTO categoryDTO1 = this.categoryService.getCategoryByCategoryID(category_id);
        return new ResponseEntity(categoryDTO1, HttpStatus.OK);
    }
    //getAll
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return new ResponseEntity<>(this.categoryService.getAllCategory(),HttpStatus.OK);
    }

}
