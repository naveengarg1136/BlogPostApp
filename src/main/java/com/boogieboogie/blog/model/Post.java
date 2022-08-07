package com.boogieboogie.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer post_id;
    @Column(nullable = false)
    private String post_title;
    @Column(nullable = false,length = 2000)
    private String post_content;
    private String imgUrl;
    private Date date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "post" ,cascade = CascadeType.ALL)
    private Set<Comment> comments= new HashSet<>();
}
