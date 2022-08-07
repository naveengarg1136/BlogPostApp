package com.boogieboogie.blog.repos;

import com.boogieboogie.blog.model.Category;
import com.boogieboogie.blog.model.Post;
import com.boogieboogie.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);


    @Query("""
            select p from Post p
            where upper(p.post_title) like upper(concat('%', ?1, '%')) or upper(p.post_content) like upper(concat('%', ?1, '%'))""")
    List<Post> findByPost_titleOrcontentContainingKeyword(String keyword);
}
