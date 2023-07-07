package com.bikkadit.electronicstore.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @Column(name="ids")
    private String id;

    @Column(name="names")
    private String name;

    @Column(name="emails")
    private String email;

    @Column(name="passwords")
    private String password;

    @Column(name="genders")
    private String gender;

    @Column(name="abouts")
    private String about;

    private String imageName;
}
