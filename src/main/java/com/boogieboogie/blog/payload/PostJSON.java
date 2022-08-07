package com.boogieboogie.blog.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostJSON {

    private Integer pageNum;
    private Integer pageSize;
    private long totalElements;
    private Integer totalPages;
    private Boolean isLastPage;
    private List<PostDTO> content;
}
