package com.codingShuttle.week3.Jpa.Tutorial.controllers;


import com.codingShuttle.week3.Jpa.Tutorial.dto.ProductDto;
import com.codingShuttle.week3.Jpa.Tutorial.service.ProductService;
import jakarta.persistence.OrderBy;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(defaultValue = "id") String sortBy) {
        List<ProductDto> products = productService.getAllProducts(Sort.by(sortBy));

        return ResponseEntity.ok(products);
    }

}
