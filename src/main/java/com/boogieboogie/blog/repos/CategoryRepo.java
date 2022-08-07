package com.boogieboogie.blog.repos;

import com.boogieboogie.blog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
