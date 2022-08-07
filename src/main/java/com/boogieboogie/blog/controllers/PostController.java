package com.boogieboogie.blog.controllers;

import com.boogieboogie.blog.config.Constants;
import com.boogieboogie.blog.payload.PostDTO;
import com.boogieboogie.blog.payload.PostJSON;
import com.boogieboogie.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Value("${project.image}")
    private String path;

    //create
    @PostMapping("/user/{userID}/category/{categoryID}")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer userID, @PathVariable Integer categoryID){
        PostDTO createdPostDTO = this.postService.createPost(postDTO, userID, categoryID);
        return new ResponseEntity<>(createdPostDTO,HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{postID}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer postID){
        PostDTO updatedPostDTO = this.postService.updatePost(postDTO,postID);
        return new ResponseEntity<>(updatedPostDTO,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{postID}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postID){
         this.postService.deletePost(postID);
        return new ResponseEntity<>(Map.of("message","Post deleted Successfully"),HttpStatus.OK);
    }

    //getPostByPostID
    @GetMapping("/{postID}")
    public ResponseEntity<PostDTO> getPostByPostID(@PathVariable Integer postID){
        PostDTO postDTO = this.postService.getPostByPostID(postID);
        return new ResponseEntity<>(postDTO,HttpStatus.OK);
    }

    //getAllPosts
    @GetMapping("/")
    public ResponseEntity<PostJSON> getAllPosts(
            @RequestParam(value = "pageNum",defaultValue = Constants.PAGE_NUMBER,required = false) Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = Constants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value ="sortBy",defaultValue = Constants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "orderBy",defaultValue = Constants.ORDER_BY,required = false) String orderBy
    ){
        PostJSON postJSON = this.postService.getAllPosts(pageNum, pageSize,sortBy,orderBy);
        return new ResponseEntity<>(postJSON,HttpStatus.OK);
    }
    //getUserSpecificPosts
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<PostDTO>> getUserSpecificPosts(@PathVariable Integer userID){
        List<PostDTO> postDTOS = this.postService.getUserSpecificPosts(userID);
        return new ResponseEntity<>(postDTOS,HttpStatus.OK);
    }

    //getCategorySpecificPosts
    @GetMapping("/category/{categoryID}")
    public ResponseEntity<List<PostDTO>> getCategorySpecificPosts(@PathVariable Integer categoryID){
        List<PostDTO> postDTOS = this.postService.getCategorySpecificPosts(categoryID);
        return new ResponseEntity<>(postDTOS,HttpStatus.OK);
    }

    //searchPostByKeyword
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPostByKeyword(@PathVariable String keyword){
        List<PostDTO> postDTOS = this.postService.searchPostByKeyword(keyword);
        return new ResponseEntity<>(postDTOS,HttpStatus.OK);
    }

    //updloadImage
    @PostMapping("/image/{postID}")
    public ResponseEntity<PostDTO> updloadImage(@RequestParam("image") MultipartFile file, @PathVariable Integer postID) throws IOException {
        PostDTO postDTO=this.postService.getPostByPostID(postID);
        String filename = this.postService.updloadImage(path, file);
        postDTO.setImgUrl(filename);
        PostDTO updatePostDTO = this.postService.updatePost(postDTO, postID);

        return new ResponseEntity<>(updatePostDTO,HttpStatus.OK);
    }

    //downloadImage
    @GetMapping(value = "/image/{imageName}", produces =MediaType.IMAGE_JPEG_VALUE )
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream inputStream = this.postService.downloadImage(path, imageName);
        System.out.println("haan chal rha hai");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());
    }
}
