package com.bloggingapis.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {
	private int id;
    private String title;
    private String content;
    private String image;
    private Date date;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<CommentDto>();
}
