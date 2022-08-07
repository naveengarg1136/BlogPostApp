package com.boogieboogie.blog.payload;


import com.boogieboogie.blog.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {


    private int user_id;
    @NotEmpty
    private String user_name;
    @NotEmpty
    @Email(message = "Please enter a valid email")
    private String email;
    @NotEmpty
    @Size(min=4,max=30,message = "Password must have atleast 4 characters")
    private String password;


}
