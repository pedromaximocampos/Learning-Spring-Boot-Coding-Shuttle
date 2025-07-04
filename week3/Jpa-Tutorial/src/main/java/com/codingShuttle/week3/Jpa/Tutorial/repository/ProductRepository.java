package com.codingShuttle.week3.Jpa.Tutorial.repository;

import com.codingShuttle.week3.Jpa.Tutorial.entities.ProductEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.logging.log4j.util.ProcessIdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository  extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByOrderByPriceDesc();

    List<ProductEntity> findBy(Sort sort);

    List<ProductEntity> findBySku(String sku);

    List<ProductEntity> findByPrice(Double price);

    List<ProductEntity> findByPriceBetween(Double lower, Double higher);

    Page<ProductEntity> findByTitleContaining(Pageable page, String title);

    List<ProductEntity> findByCreatedAtAfter(LocalDateTime createdAt);

    List<ProductEntity> findFirstByTitle(String title);

    List<ProductEntity> findTop3ByTitle(String title);

    List<ProductEntity> findTop3ByTitleContaining(String title);

    List<ProductEntity> findByQuantityGreaterThan(Integer quantity);

    List<ProductEntity> findByPriceGreaterThan(Double price);

    Optional<ProductEntity> findByTitleAndDescription(String title, String description);

    List<ProductEntity> findByTitleAndPriceOrderByTitle(String title, Double price);


    @Query("select  e from ProductEntity e where e.title=?1 and e.price=?2")
    Optional<ProductEntity> findByTitleAndPrice(String title, Double price);
}
