package com.week4.prod_ready_features.prod_ready_features;

import com.week4.prod_ready_features.prod_ready_features.clients.EmployeeClient.EmployeeClient;
import com.week4.prod_ready_features.prod_ready_features.dto.Employees.EmployeeDTO;
import com.week4.prod_ready_features.prod_ready_features.entities.UserEntity;
import com.week4.prod_ready_features.prod_ready_features.services.Auth.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class ProdReadyFeaturesApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;

	@Autowired
	private JwtService jwtService;

	@BeforeAll
	static void setupAll(){
		log.info("Setting up all methods");
	}

	@BeforeEach
	void setupEach(){
		log.info("Setting up each method");
	}

	@AfterAll
	static void finishingAll(){
		log.info("Finishig all methods");
	}

	@AfterEach
	void finishEach(){
		log.info("Finishing each method");
	}


	@Test
	void tryingToSumTwoIntegersNumbers(){
		int a = 3;
		int b = 5;

		int result = sumTwoNumbers(a, b);

		Assertions.assertThat(result)
				.isEqualTo(8)
				.isCloseTo(9, Offset.offset(1));


//		Assertions.assertThat("Apple")
//				.isEqualTo("Apple")
//				.hasSize(5)
//				.startsWith("App")
//				.endsWith("le");
	}


	@Test
	void divideTwoNumbers_whenTheDenominatorIsEqualToZero(){
		int a = 4;
		int b = 0;

		Assertions.assertThatThrownBy(() -> divideTwoNumbers(a, b))
				.isInstanceOf(ArithmeticException.class)
				.hasMessage("division by zero");
	}


	int sumTwoNumbers(int a, int b){
		return a + b;
	}


	double divideTwoNumbers(int a, int b){
		try{
			double result =  a / b;

			return result;
		}catch (ArithmeticException e){
			throw  new ArithmeticException("division by zero");
		}
	}


	//@Test
	@Order(3)
	void getAllEmployeeTest() {
		List<EmployeeDTO> employeeDTOList = employeeClient.getAllEmployees();
		System.out.println(employeeDTOList);
	}


	//@Test
	@Order(2)
	void getEmployeeByIdTest() {
		EmployeeDTO employeeDTO = employeeClient.getEmployeeById(1L);
		System.out.println(employeeDTO);
	}

	//@Test
	@Order(1)
	void createNewEmployeeTest() {
		EmployeeDTO employeeDTO = employeeClient.createEmployee(
				new EmployeeDTO(null, "Pedro", "pedromaximocc@gmail.com",
						LocalDate.of(2025, 9, 2) ,
						 true, "Akhaadjh++jd", 13)
		);

		EmployeeDTO createdEmployeeDTO = employeeClient.createEmployee(employeeDTO);
		System.out.println(createdEmployeeDTO);
	}

	@Test
	void createJwtTokenTest(){

		UserEntity user =  new UserEntity();
		user.setId(1L);
		user.setEmail("pedro@gmail.com");
		user.setPassword("dj8dqjwdhqjd");
		String token = jwtService.generateAccessToken(user);

		System.out.println("Generated Token: " + token);

		Long userId = jwtService.getUserIdFromToken(token);
		System.out.println("Extracted User ID from Token: " + userId);

	}





}
