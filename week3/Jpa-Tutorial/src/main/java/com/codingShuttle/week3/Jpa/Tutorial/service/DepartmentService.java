package com.codingShuttle.week3.Jpa.Tutorial.service;


import com.codingShuttle.week3.Jpa.Tutorial.dto.DepartmentDto;
import com.codingShuttle.week3.Jpa.Tutorial.entities.DepartmentEntity;
import com.codingShuttle.week3.Jpa.Tutorial.entities.EmployeeEntity;
import com.codingShuttle.week3.Jpa.Tutorial.repository.DepartmentRepository;
import com.codingShuttle.week3.Jpa.Tutorial.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {


    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public DepartmentDto getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDto.class))
                .orElse(null);
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        DepartmentEntity departmentEntity = this.modelMapper.map(departmentDto, DepartmentEntity.class);
        DepartmentEntity result = departmentRepository.save(departmentEntity);
        return this.modelMapper.map(result, DepartmentDto.class);
    }


    public DepartmentDto assignManagerToDepartement(Long departmentId, Long employeeId) {
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        departmentEntity.setManager(employeeEntity);
        DepartmentEntity updatedDepartment = departmentRepository.save(departmentEntity);

        return modelMapper.map(updatedDepartment, DepartmentDto.class);
    }


    public DepartmentDto assingWorkerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);


        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);

         DepartmentEntity updatedDepartment = departmentEntity.flatMap(department ->{
             return employeeEntity.map(employee -> {
               employee.setWorkingDepartment(department);
               employeeRepository.save(employee);
               department.getEmployees().add(employee);

               return department;
           });
        }).orElseThrow( () -> new RuntimeException("Department or Employee not found") );

       return modelMapper.map(updatedDepartment, DepartmentDto.class);
    }

}
