package com.codingShuttle.week3.Jpa.Tutorial.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPageResponseDto {
    private int totalPages;
    private List<ProductDto> products;
}