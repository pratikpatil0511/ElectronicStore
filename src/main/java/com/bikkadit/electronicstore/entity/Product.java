package com.bikkadit.electronicstore.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
}
