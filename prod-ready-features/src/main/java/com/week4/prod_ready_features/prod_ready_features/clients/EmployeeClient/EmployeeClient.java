package com.week4.prod_ready_features.prod_ready_features.clients.EmployeeClient;

import com.week4.prod_ready_features.prod_ready_features.dto.Employees.EmployeeDTO;

import java.util.List;

public interface EmployeeClient {

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(Long id);

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
}
