package com.boogieboogie.blog.services.impl;

import com.boogieboogie.blog.exceptions.ResourseNotFoundException;
import com.boogieboogie.blog.model.Category;
import com.boogieboogie.blog.model.Post;
import com.boogieboogie.blog.model.User;
import com.boogieboogie.blog.payload.PostDTO;
import com.boogieboogie.blog.payload.PostJSON;
import com.boogieboogie.blog.repos.CategoryRepo;
import com.boogieboogie.blog.repos.PostRepo;
import com.boogieboogie.blog.repos.UserRepo;
import com.boogieboogie.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    private PostDTO postTOPostDTO(Post post){
        return this.modelMapper.map(post,PostDTO.class);
    }
    private Post postDTOToPost(PostDTO postDTO){
        return this.modelMapper.map(postDTO,Post.class);
    }

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userID, Integer categoryID) {
        Post post= this.postDTOToPost(postDTO);
        User user=this.userRepo.findById(userID).orElseThrow(()-> new ResourseNotFoundException("User","user Id", userID));
        Category category=this.categoryRepo.findById(categoryID).orElseThrow(()-> new ResourseNotFoundException("Category","categoryId",categoryID));
        post.setUser(user);
        post.setCategory(category);
        post.setDate(new Date());
        Post post1 = this.postRepo.save(post);
        return this.postTOPostDTO(post1);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postID) {
        Post post= this.postRepo.findById(postID).orElseThrow(()-> new ResourseNotFoundException("Post","Post Id", postID));
        post.setPost_content(postDTO.getPost_content());
        post.setPost_title(postDTO.getPost_title());
        post.setImgUrl(postDTO.getImgUrl());
        Post post1 = this.postRepo.save(post);
        return this.postTOPostDTO(post1);
    }

    @Override
    public void deletePost(Integer postID) {
        Post post= this.postRepo.findById(postID).orElseThrow(()-> new ResourseNotFoundException("Post","Post Id", postID));
        this.postRepo.delete(post);
    }

    @Override
    public PostDTO getPostByPostID(Integer postID) {
        Post post= this.postRepo.findById(postID).orElseThrow(()-> new ResourseNotFoundException("Post","Post Id", postID));
        return this.postTOPostDTO(post);
    }

    @Override
    public PostJSON getAllPosts(Integer pageNum, Integer pageSize ,String sortBy, String orderBy) {

        Sort sort=  (sortBy.equalsIgnoreCase("ASC")) ? Sort.by(sortBy).ascending() :Sort.by(sortBy).descending();

        Pageable p= PageRequest.of(pageNum,pageSize,sort);
        Page<Post> pagePost = this.postRepo.findAll(p);// GetContent() is required to convert page to List
        List<Post> posts = pagePost.getContent();
        List<PostDTO> postDTOS = posts.stream().map((post) -> this.postTOPostDTO(post)).collect(Collectors.toList());

        PostJSON postJSON=new PostJSON();
        postJSON.setContent(postDTOS);
        postJSON.setPageNum(pagePost.getNumber());
        postJSON.setPageSize(pagePost.getSize());
        postJSON.setTotalElements(pagePost.getTotalElements());
        postJSON.setIsLastPage(pagePost.isLast());
        postJSON.setTotalPages(pagePost.getTotalPages());
        return postJSON;
    }

    @Override
    public List<PostDTO> getUserSpecificPosts(Integer userID) {
        User user=this.userRepo.findById(userID).orElseThrow(()-> new ResourseNotFoundException("User","user Id", userID));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDTO> postDTOS = posts.stream().map((post) -> this.postTOPostDTO(post)).collect(Collectors.toList());
        return postDTOS;
    }

    @Override
    public List<PostDTO> getCategorySpecificPosts(Integer categoryID) {
        Category category=this.categoryRepo.findById(categoryID).orElseThrow(()-> new ResourseNotFoundException("Category","categoryId",categoryID));
        List<Post> posts = this.postRepo.findByCategory(category);
        List<PostDTO> postDTOS = posts.stream().map((post) -> this.postTOPostDTO(post)).collect(Collectors.toList());
        return postDTOS;
    }

    @Override
    public List<PostDTO> searchPostByKeyword(String keyword) {
        List<Post> posts = this.postRepo.findByPost_titleOrcontentContainingKeyword(keyword);
        return posts.stream().map((post) -> this.postTOPostDTO(post)).collect(Collectors.toList());
    }

    @Override
    public String updloadImage(String path, MultipartFile file) throws IOException {

        //file name nikalna
        String name= file.getOriginalFilename();
        String RandomID= UUID.randomUUID().toString();
        String randomName= RandomID.concat(name.substring(name.lastIndexOf(".")));

        //full path bnana
        String fullpath= path+ File.separator + randomName;

        //create folder if not exist
        File file1 = new File(path);
        if(!file1.exists()) file1.mkdir();


        //copy file in folder
        Files.copy(file.getInputStream(), Paths.get(fullpath));
        return randomName;
    }

    @Override
    public InputStream downloadImage(String path, String fileName) throws FileNotFoundException {

        String fullPath= path + File.separator + fileName;
        InputStream fileInputStream = new FileInputStream(fullPath);
        return fileInputStream;
    }
}
