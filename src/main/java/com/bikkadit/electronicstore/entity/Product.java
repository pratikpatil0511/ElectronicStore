package com.bikkadit.electronicstore.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @Column(name="ids")
    private String id;

    @Column(name="titles")
    private String title;

    @Column(name="descriptions")
    private String description;

    @Column(name="prices")
    private int price;

    private int discountedPrice;

    @Column(name="quantities")
    private int quantity;

    private Date addedDate;

    private boolean live;

    private boolean stock;

    private String imageName;

    //mapping with Category
    @ManyToOne(fetch = FetchType.EAGER) //when we fetch Product then also Category will be fetched also
    @JoinColumn(name = "productCategory")
    private Category category;
}
