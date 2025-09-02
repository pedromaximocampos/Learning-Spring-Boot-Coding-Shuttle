package com.week4.prod_ready_features.prod_ready_features;

import com.week4.prod_ready_features.prod_ready_features.clients.EmployeeClient.EmployeeClient;
import com.week4.prod_ready_features.prod_ready_features.dto.Employees.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RequiredArgsConstructor
class ProdReadyFeaturesApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;


	@Test
	@Order(3)
	void getAllEmployeeTest() {
		List<EmployeeDTO> employeeDTOList = employeeClient.getAllEmployees();
		System.out.println(employeeDTOList);
	}


	@Test
	@Order(2)
	void getEmployeeByIdTest() {
		EmployeeDTO employeeDTO = employeeClient.getEmployeeById(1L);
		System.out.println(employeeDTO);
	}

	@Test
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

}
