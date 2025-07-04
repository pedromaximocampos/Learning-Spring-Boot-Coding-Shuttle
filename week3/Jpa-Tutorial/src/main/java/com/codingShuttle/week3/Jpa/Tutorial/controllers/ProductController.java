package com.codingShuttle.week3.Jpa.Tutorial.controllers;


import com.codingShuttle.week3.Jpa.Tutorial.dto.ProductDto;
import com.codingShuttle.week3.Jpa.Tutorial.dto.ProductPageResponseDto;
import com.codingShuttle.week3.Jpa.Tutorial.service.ProductService;
import jakarta.persistence.OrderBy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {


    private final ProductService productService;

    private static final int pageSize = 10; // Default page size

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private static Pageable getPageable(Integer pageNumber, String sortBy, String orderBy) {


        Sort.Direction direction = getSortDirection(orderBy);
        return PageRequest.of(pageNumber - 1, pageSize, Sort.by(direction, sortBy));
    }

    private static Sort.Direction getSortDirection(String orderBy ) {

        if (orderBy == null || orderBy.isEmpty()) {
            return Sort.Direction.ASC; // Default to ascending if no order is specified
        }
        return orderBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(defaultValue = "id") String sortBy,

                                                           @RequestParam(defaultValue = "asc") String orderBy) {

        Sort.Direction direction = ProductController.getSortDirection(orderBy);
        List<ProductDto> products = productService.getAllProducts(Sort.by(direction,  sortBy));

        return ResponseEntity.ok(products);
    }


    @GetMapping(path="/pageable")
    public ResponseEntity<List<ProductDto>> getAllProductsPageable(@RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String orderBy,
                                                           @RequestParam(defaultValue = "1") Integer pageNumber) {
        Sort.Direction direction = ProductController.getSortDirection(orderBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));
        List<ProductDto> products = productService.getAllProductsPageble(pageable);
        return ResponseEntity.ok(products);
    }


    @GetMapping(path = "/{productTitle}")
    public ResponseEntity<ProductPageResponseDto> getProductByContainingTitle(@PathVariable String productTitle, @RequestParam(defaultValue = "id") String sortBy,
                                                                              @RequestParam(defaultValue = "asc") String orderBy, @RequestParam(defaultValue = "1") Integer pageNumber) {
        Pageable page = getPageable(pageNumber, sortBy, orderBy);
        ProductPageResponseDto productsResponseDto = productService.getProductsByTitle(page, productTitle);

        return ResponseEntity.ok(productsResponseDto);
    }
}
