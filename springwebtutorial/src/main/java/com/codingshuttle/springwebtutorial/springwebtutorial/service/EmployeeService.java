package com.codingshuttle.springwebtutorial.springwebtutorial.service;

import com.codingshuttle.springwebtutorial.springwebtutorial.dto.EmployeeDto;
import com.codingshuttle.springwebtutorial.springwebtutorial.entity.EmployeeEntity;
import com.codingshuttle.springwebtutorial.springwebtutorial.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDto getOneEmployeeById(Long id) {
        EmployeeEntity employeeEntity = this.employeeRepository.findById(id).orElse(null);
        return modelMapper.map(employeeEntity, EmployeeDto.class);
    }

    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = this.employeeRepository.findAll();
        List<EmployeeDto> employeeDtos;
        employeeDtos = employeeEntities
                            .stream()
                            .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                            .toList();
        return employeeDtos;
    }

    public EmployeeDto saveOneEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employeeEntity = this.modelMapper.map(employeeDto, EmployeeEntity.class);
        EmployeeEntity result = this.employeeRepository.save(employeeEntity);
        return modelMapper.map(result, EmployeeDto.class);
    }

}
