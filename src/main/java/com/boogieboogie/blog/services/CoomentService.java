package com.boogieboogie.blog.services;

import com.boogieboogie.blog.payload.CommentDTO;

import java.util.List;

public interface CoomentService {

    // create comment
    CommentDTO createComment(CommentDTO commentDTO,Integer postId);
    //updatecomment
    CommentDTO updateComment(CommentDTO commentDTO,Integer commentId);

    //deleteComment
    void deleteComment(Integer commentID);

    //getAllCommentofSpecificPost
    List<CommentDTO> getAllCommentofSpecificPost(Integer postId);

}
