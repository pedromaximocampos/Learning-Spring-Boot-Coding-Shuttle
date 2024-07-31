package com.codingshuttle.springwebtutorial.springwebtutorial.service;

import com.codingshuttle.springwebtutorial.springwebtutorial.dto.EmployeeDto;
import com.codingshuttle.springwebtutorial.springwebtutorial.entity.EmployeeEntity;
import com.codingshuttle.springwebtutorial.springwebtutorial.exceptions.NoSuchResouceFound;
import com.codingshuttle.springwebtutorial.springwebtutorial.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public void isExistent(Long employeeId){
        boolean exists =  employeeRepository.existsById(employeeId);
        if(!exists) throw new NoSuchResouceFound("Employee with id "+employeeId+" does not exist");
    }

    public EmployeeDto getOneEmployeeById(Long id) {
        isExistent(id);
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

    public EmployeeDto putEmployeeById(Long employeeId, EmployeeDto employeeDto) {
        isExistent(employeeId);
        EmployeeEntity newEmployeeEntity = this.modelMapper.map(employeeDto, EmployeeEntity.class);
        newEmployeeEntity.setId(employeeId);
        EmployeeEntity result = this.employeeRepository.save(newEmployeeEntity);
        return modelMapper.map(result, EmployeeDto.class);
    }



    public EmployeeDto deleteEmployeeById(Long employeeId) {
        isExistent(employeeId);
        EmployeeEntity employeeEntity = this.employeeRepository.findById(employeeId).get();
        this.employeeRepository.delete(employeeEntity);
        return modelMapper.map(employeeEntity, EmployeeDto.class);

    }

    public EmployeeDto updateEmployeePropertiesById(Long employeeId, Map<String, Object> updates) {
        isExistent(employeeId);
        EmployeeEntity employeeEntity = this.employeeRepository.findById(employeeId).get();

        // Java Reflection ...
        updates.forEach((key, value) -> {
            Field employeefield = ReflectionUtils.findField((EmployeeEntity.class), key);
            assert employeefield != null;
            employeefield.setAccessible(true);
            ReflectionUtils.setField(employeefield, employeeEntity, value);
        });
        this.employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity, EmployeeDto.class);
    }
}
