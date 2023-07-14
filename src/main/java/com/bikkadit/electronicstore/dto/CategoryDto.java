package com.bikkadit.electronicstore.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CategoryDto {

    private String id;

    @Size(min = 4,message = "Title should be of atleast 4 characters")
    @NotBlank
    private String title;

    @NotEmpty
    private String description;

    private String coverImage;
}
