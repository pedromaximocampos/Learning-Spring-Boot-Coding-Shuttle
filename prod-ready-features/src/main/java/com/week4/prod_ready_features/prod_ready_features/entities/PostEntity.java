package com.week4.prod_ready_features.prod_ready_features.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class PostEntity extends AuditableEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;
}
