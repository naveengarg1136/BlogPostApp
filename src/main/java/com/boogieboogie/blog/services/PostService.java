package com.boogieboogie.blog.services;

import com.boogieboogie.blog.payload.PostDTO;
import com.boogieboogie.blog.payload.PostJSON;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface PostService {

    //create
    PostDTO createPost(PostDTO postDTO, Integer userID, Integer categoryID);

    //update
    PostDTO updatePost(PostDTO postDTO, Integer postID);

    //delete
    void deletePost(Integer postID);

    //getPostByPostID
    PostDTO getPostByPostID(Integer postID);

    //getAllPosts
    PostJSON getAllPosts(Integer pageNum, Integer pageSize ,String sortBy, String orderBy);

    //getUserSpecificPosts
    List<PostDTO> getUserSpecificPosts(Integer userID);

    //getCategorySpecificPosts
    List<PostDTO> getCategorySpecificPosts(Integer categoryID);

    //searchPostByKeyword
    List<PostDTO> searchPostByKeyword(String keyword);

    //updloadImage
    String updloadImage(String path, MultipartFile file) throws IOException;

    //downloadImage
    InputStream downloadImage(String path,String fileName) throws FileNotFoundException;
}
