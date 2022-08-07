package com.boogieboogie.blog.payload;


import com.boogieboogie.blog.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private Integer post_id;
    @NotBlank
    private String post_title;
    @NotBlank
    private String post_content;
    private String imgUrl;
    private Date date;
    private UserDto user;

}
