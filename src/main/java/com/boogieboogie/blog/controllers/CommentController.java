package com.boogieboogie.blog.controllers;

import com.boogieboogie.blog.payload.CommentDTO;
import com.boogieboogie.blog.services.CoomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CoomentService coomentService;

    @PostMapping("/post/{postID}")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer postID){
        CommentDTO createdComment = this.coomentService.createComment(commentDTO, postID);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{commentID}")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer commentID){
        CommentDTO createdComment = this.coomentService.updateComment(commentDTO, commentID);
        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }
    @DeleteMapping("/{commentID}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentID){
        this.coomentService.deleteComment(commentID);
        return new ResponseEntity<>(Map.of("message","Comment deleted!"), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentofSpecificPost(@PathVariable Integer postId){
        List<CommentDTO> allCommentofSpecificPost = this.coomentService.getAllCommentofSpecificPost(postId);
        return new ResponseEntity<>(allCommentofSpecificPost,HttpStatus.OK);
    }
}
