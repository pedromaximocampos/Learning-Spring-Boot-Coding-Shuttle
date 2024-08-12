package com.codingShuttle.week3.Jpa.Tutorial;

import com.codingShuttle.week3.Jpa.Tutorial.entities.ProductEntity;
import com.codingShuttle.week3.Jpa.Tutorial.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class JpaTutorialApplicationTests {


	@Autowired
	ProductRepository productRepository;


    @Test
	void contextLoads() {
	}

	@Test
	void saveProduct(){
		ProductEntity productEntity = ProductEntity
				.builder()
				.sku("nestle123")
				.price(13.5490F)
				.title("Chocolate")
				.description("Dark Chocollate")
				.build();

		ProductEntity savedProduct  = this.productRepository.save(productEntity);

		System.out.println(savedProduct);
	}

	@Test
	void getRepository(){
		List<ProductEntity> products = this.productRepository.findAll();

		for(ProductEntity productEntity : products){
			System.out.println(productEntity);
		}
	}



}
