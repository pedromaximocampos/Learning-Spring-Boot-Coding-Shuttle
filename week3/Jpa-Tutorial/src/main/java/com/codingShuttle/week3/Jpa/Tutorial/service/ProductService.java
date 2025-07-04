package com.codingShuttle.week3.Jpa.Tutorial.service;


import com.codingShuttle.week3.Jpa.Tutorial.dto.ProductDto;
import com.codingShuttle.week3.Jpa.Tutorial.dto.ProductPageResponseDto;
import com.codingShuttle.week3.Jpa.Tutorial.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }



    public List<ProductDto> getAllProducts(Sort sortBy) {

        return productRepository.findBy(sortBy).stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
                .collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductsPageble(Pageable page) {
        return  productRepository.findAll(page).stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
                .collect(Collectors.toList());
    }


    public ProductPageResponseDto getProductsByTitle(Pageable page, String title) {
        Page<?> productsPage =  productRepository.findByTitleContaining(page, title);
        List<ProductDto> products = productsPage.stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductDto.class))
                .toList();

        ProductPageResponseDto productPageResponseDto = new ProductPageResponseDto();
        productPageResponseDto.setTotalPages(productsPage.getTotalPages());
        productPageResponseDto.setProducts(products);

        return productPageResponseDto;
    }
}
