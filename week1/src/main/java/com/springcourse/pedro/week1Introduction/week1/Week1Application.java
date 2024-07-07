package com.springcourse.pedro.week1Introduction.week1;

import jakarta.annotation.security.RunAs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Week1Application implements CommandLineRunner {

	@Autowired
	Apple obj;

	@Autowired
	DBService dbService;


	public static void main(String[] args) {
		SpringApplication.run(Week1Application.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		obj.eat();
		System.out.println(dbService.getData());
	}


}
