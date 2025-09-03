package com.week4.prod_ready_features.prod_ready_features.clients.EmployeeClient.Impl;

import com.week4.prod_ready_features.prod_ready_features.advices.ApiResponse;
import com.week4.prod_ready_features.prod_ready_features.clients.EmployeeClient.EmployeeClient;
import com.week4.prod_ready_features.prod_ready_features.dto.Employees.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    private Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.trace("Fetching all employees from external service, method=getAllEmployees");
        try{
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                                                        .uri("/employees")
                                                        .retrieve()
                                                        .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                                                            log.error("Client error while fetching employees: {}", res.getBody());
                                                            throw new RuntimeException("Client Error: " + res.getBody());
                                                        })
                                                        .body(new ParameterizedTypeReference<>() {
                                                        });
            log.debug("Successfully fetched {} employees", employeeDTOList.getData().size());
            log.trace("Employee data sample: {}", employeeDTOList.getData());
            return employeeDTOList.getData();
        }catch (Exception e){
            log.error("Error fetching employees: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        log.trace("Fetching employee with ID {} from external service, method=getEmployeeById", id);
        try{
            ApiResponse<EmployeeDTO> employeeDTO = restClient.get()
                                                    .uri("/employees/{employeeId}", id)
                                                    .retrieve()
                                                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                                                        log.error("Client error while fetching employee with ID {}: {}", id, res.getBody());
                                                        throw new RuntimeException("Client Error: " + res.getBody());
                                                    })
                                                    .body(new ParameterizedTypeReference<>() {
                                                    });
            log.debug("Successfully fetched employee with ID {}", id);
            log.trace("Fetched employee data: {}", employeeDTO.getData());
            return employeeDTO.getData();
        }catch (Exception e){
            log.error("Error fetching employee with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.trace("Creating new employee in external service, method=createEmployee, payload={}", employeeDTO);
        try{
            ResponseEntity<ApiResponse<EmployeeDTO>> createdEmployeeDTO = restClient.post()
                    .uri("/employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error("Client error while creating employee: {}", res.getBody());
                        throw new RuntimeException("Client Error: " + res.getBody());
                    })
                    .toEntity(new ParameterizedTypeReference<>() {
                    });
            log.debug("Successfully created employee with ID {}", createdEmployeeDTO.getBody().getData().getId());
            log.trace("Created employee data: {}", createdEmployeeDTO.getBody().getData());
            return createdEmployeeDTO.getBody().getData();
        }catch (Exception e){
            log.error("Error creating employee: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }
}
