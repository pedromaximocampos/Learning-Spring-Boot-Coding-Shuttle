package com.week4.prod_ready_features.prod_ready_features.clients.EmployeeClient.Impl;

import com.week4.prod_ready_features.prod_ready_features.advices.ApiResponse;
import com.week4.prod_ready_features.prod_ready_features.clients.EmployeeClient.EmployeeClient;
import com.week4.prod_ready_features.prod_ready_features.dto.Employees.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try{
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                                                        .uri("/employees")
                                                        .retrieve()
                                                        .body(new ParameterizedTypeReference<>() {
                                                        });
            return employeeDTOList.getData();
        }catch (Exception e){
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        try{
            ApiResponse<EmployeeDTO> employeeDTO = restClient.get()
                                                    .uri("/employees/{employeeId}", id)
                                                    .retrieve()
                                                    .body(new ParameterizedTypeReference<>() {
                                                    });
            return employeeDTO.getData();
        }catch (Exception e){
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        try{
            ApiResponse<EmployeeDTO> createdEmployeeDTO = restClient.post()
                    .uri("/employees")
                    .body(employeeDTO)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return createdEmployeeDTO.getData();
        }catch (Exception e){
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }
}
