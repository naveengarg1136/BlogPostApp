package com.boogieboogie.blog.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDTO {

    private Integer category_id;
    @NotBlank
    @Size(min=3)
    private String category_title;
    @NotBlank
    @Size(min=10)
    private String category_description;
}
