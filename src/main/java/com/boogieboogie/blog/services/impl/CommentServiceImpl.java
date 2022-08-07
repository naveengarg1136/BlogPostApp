package com.boogieboogie.blog.services.impl;

import com.boogieboogie.blog.exceptions.ResourseNotFoundException;
import com.boogieboogie.blog.model.Comment;
import com.boogieboogie.blog.model.Post;
import com.boogieboogie.blog.payload.CommentDTO;
import com.boogieboogie.blog.repos.CommentRepo;
import com.boogieboogie.blog.repos.PostRepo;
import com.boogieboogie.blog.services.CoomentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CoomentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    private CommentDTO commentToDTO(Comment comment){
        return this.modelMapper.map(comment, CommentDTO.class);
    }
    private Comment commentDTOToComment(CommentDTO commentDTO){
        return this.modelMapper.map(commentDTO,Comment.class);
    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourseNotFoundException("Post", "PostID", postId));
        Comment comment = commentDTOToComment(commentDTO);
        comment.setPost(post);

        Comment savedComment = this.commentRepo.save(comment);
        return this.commentToDTO(savedComment);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourseNotFoundException("Comment", "ID", commentId));
        comment.setText(commentDTO.getText());
        Comment save = this.commentRepo.save(comment);
        return this.commentToDTO(save);
    }

    @Override
    public void deleteComment(Integer commentID) {
        Comment comment = this.commentRepo.findById(commentID).orElseThrow(() -> new ResourseNotFoundException("Comment", "ID", commentID));
        this.commentRepo.delete(comment);
    }

    @Override
    public List<CommentDTO> getAllCommentofSpecificPost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourseNotFoundException("Post", "PostID", postId));
        List<Comment> comments = this.commentRepo.findByPost(post);
        List<CommentDTO> commentDTOS = comments.stream().map((comment -> this.commentToDTO(comment))).collect(Collectors.toList());

        return commentDTOS;
    }
}
