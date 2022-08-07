package com.boogieboogie.blog.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CommentDTO {

    private Integer commentID;
    @NotBlank
    private String text;

}
