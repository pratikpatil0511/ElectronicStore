package com.bikkadit.electronicstore.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
