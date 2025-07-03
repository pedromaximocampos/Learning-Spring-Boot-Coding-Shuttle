package com.codingShuttle.week3.Jpa.Tutorial;

import com.codingShuttle.week3.Jpa.Tutorial.entities.ProductEntity;
import com.codingShuttle.week3.Jpa.Tutorial.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

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
				.sku("nestle123467")
				.price(15.5490)
				.title("Chocolate nestle teste3")
				.description("Dark Chocollate")
				.quantity(100)
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


	@Test
	void findBySku() {
		List<ProductEntity> products = this.productRepository.findBySku("nestle123");

		System.out.println("Products with SKU 'nestle123':");

		for (ProductEntity productEntity : products) {
			System.out.println(productEntity);
		}
	}

	@Test
	void findByPriceGreaterThan() {
		List<ProductEntity> products = this.productRepository.findByPriceGreaterThan(13.00);

		System.out.println("Products with price greater than 13.00:");

		for (ProductEntity productEntity : products) {
			System.out.println(productEntity);
		}
	}

	@Test
	void findSingleEntityByTitleAndDescription() {
		String title = "Chocolate nestle teste3";
		String description = "Dark Chocollate";

		Optional<ProductEntity> productEntity = this.productRepository.findByTitleAndDescription(title, description);

		if (productEntity.isPresent()) {
			System.out.println("Product found: " + productEntity.get());
		} else {
			System.out.println("No product found with title '" + title + "' and description '" + description + "'");
		}
	}


}
