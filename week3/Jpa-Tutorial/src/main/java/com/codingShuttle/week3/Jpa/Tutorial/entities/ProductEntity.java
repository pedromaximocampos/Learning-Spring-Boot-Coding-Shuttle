package com.codingShuttle.week3.Jpa.Tutorial.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(name="sku_unique", columnNames = {"sku"}),
                @UniqueConstraint(name="title_price_unique", columnNames = {"product_title", "price"})
        },
        indexes = {
                @Index(name="title_index", columnList = "product_title"),
                @Index(name="sku_index", columnList = "sku")
        }
)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String sku;

    @Column(nullable = false, name="product_title")
    private String title;

    @Column(length = 150)
    private String description;

    private Integer quantity;

    private Double price;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
