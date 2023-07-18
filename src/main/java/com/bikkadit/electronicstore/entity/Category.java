package com.bikkadit.electronicstore.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Categories")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Category {

    @Id
    @Column(name="ids")
    private String id;

    @Column(name="titles")
    private String title;

    @Column(name="descriptions")
    private String description;

    private String coverImage;

    //mapping with Product
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY) //when we fetch Category,Product won't be fetched
    private List<Product> products=new ArrayList<>();
}
