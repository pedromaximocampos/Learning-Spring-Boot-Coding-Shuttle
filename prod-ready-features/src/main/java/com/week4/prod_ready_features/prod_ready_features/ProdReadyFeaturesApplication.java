package com.week4.prod_ready_features.prod_ready_features;

import com.week4.prod_ready_features.prod_ready_features.services.Data.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class ProdReadyFeaturesApplication implements CommandLineRunner {

	private final DataService dataService;

	public static void main(String[] args) {
		SpringApplication.run(ProdReadyFeaturesApplication.class, args);
	}

	@Override
	public void run(String... args)throws Exception{
		System.out.println("this is: "+ dataService.getData());
	}

}
