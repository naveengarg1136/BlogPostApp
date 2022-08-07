package com.boogieboogie.blog.repos;

import com.boogieboogie.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
