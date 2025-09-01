package com.week4.prod_ready_features.prod_ready_features.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
@EntityListeners(AuditableEntity.class)
public class PostEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;
}
