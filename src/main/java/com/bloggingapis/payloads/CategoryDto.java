package com.bloggingapis.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private int id;
    @NotEmpty
    @Size(min = 4 , message = "Min Category title is 4")
    private String title;
    @NotEmpty
    @Size(min = 10 , message = "Min Category description is 10")
    private String description;
}
