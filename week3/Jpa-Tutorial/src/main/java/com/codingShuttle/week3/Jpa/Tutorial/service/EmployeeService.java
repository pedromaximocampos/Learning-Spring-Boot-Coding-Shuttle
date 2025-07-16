package com.codingShuttle.week3.Jpa.Tutorial.service;


import com.codingShuttle.week3.Jpa.Tutorial.dto.EmployeeDto;
import com.codingShuttle.week3.Jpa.Tutorial.entities.EmployeeEntity;
import com.codingShuttle.week3.Jpa.Tutorial.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        EmployeeEntity employeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class);
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }


    public EmployeeDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDto.class))
                .orElse(null);
    }

}
