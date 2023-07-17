package com.bikkadit.electronicstore.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDto {

    private String id;

    @Size(min = 4,message = "Title must be of atleast of 4 characters")
    private String title;

    @NotEmpty
    private String description;

    @NotBlank
    private int price;

    private int discountedPrice;

    @NotBlank
    private int quantity;

    private Date addedDate;

    @NotBlank
    private boolean live;

    @NotBlank
    private boolean stock;

}
