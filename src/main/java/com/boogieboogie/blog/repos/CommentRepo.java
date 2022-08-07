package com.boogieboogie.blog.repos;

import com.boogieboogie.blog.model.Comment;
import com.boogieboogie.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface CommentRepo extends JpaRepository<Comment, Integer> {
        List<Comment> findByPost(Post post);
}
