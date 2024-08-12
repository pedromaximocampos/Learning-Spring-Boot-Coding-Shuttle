package com.codingShuttle.week3.Jpa.Tutorial.repository;

import com.codingShuttle.week3.Jpa.Tutorial.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository  extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findBySku(String sku);

    List<ProductEntity> findByPrice(Double price);

    List<ProductEntity> findByPriceBetween(Double lower, Double higher);

    List<ProductEntity> findByTitleContaining(String title);

    List<ProductEntity> findCreatedAfter(LocalDateTime createdAt);

    @Query("select  e from ProductEntity e where e.title=?1 and e.price=?2")
    Optional<ProductEntity> findByTitleAndPrice(String title, Double price);
}
