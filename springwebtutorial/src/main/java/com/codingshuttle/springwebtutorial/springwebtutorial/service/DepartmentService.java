package com.codingshuttle.springwebtutorial.springwebtutorial.service;

import com.codingshuttle.springwebtutorial.springwebtutorial.dto.DepartmentDto;
import com.codingshuttle.springwebtutorial.springwebtutorial.entity.DepartmentEntity;
import com.codingshuttle.springwebtutorial.springwebtutorial.exceptions.NoSuchResouceFound;
import com.codingshuttle.springwebtutorial.springwebtutorial.repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;


    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    public void isExistent(Long departmentId) {
        boolean isExistent = departmentRepository.existsById(departmentId);
        if (!isExistent) {
            throw new NoSuchResouceFound("Department with id: " + departmentId + " does not exist");
        }
    }

    public List<DepartmentDto> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        List<DepartmentDto> departmentDtos;
        departmentDtos = departmentEntities
                .stream()
                .map(department -> this.modelMapper.map(department, DepartmentDto.class))
                .toList();
        return departmentDtos;
    }

    public DepartmentDto getDepartmentById(Long departmentId) {
        this.isExistent(departmentId);
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).get();
        return this.modelMapper.map(departmentEntity, DepartmentDto.class);

    }

    public DepartmentDto createNewDepartment(DepartmentDto departmentDto) {
        DepartmentEntity departmentEntity = this.modelMapper.map(departmentDto, DepartmentEntity.class);
        this.departmentRepository.save(departmentEntity);
        return this.modelMapper.map(departmentEntity, DepartmentDto.class);
    }

    public DepartmentDto deleteDepartmentById(Long departmentId) {
        this.isExistent(departmentId);
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).get();
        departmentRepository.deleteById(departmentId);
        return this.modelMapper.map(departmentEntity, DepartmentDto.class);
    }

    public List<DepartmentDto> deleteAllDepartments() {
        List<DepartmentDto> departmentDtos = this.getAllDepartments();
        departmentRepository.deleteAll();
        return departmentDtos;
    }

    public DepartmentDto updateEntireDepartment(DepartmentDto departmentDto, Long departmentId) {
        this.isExistent(departmentId);
        DepartmentEntity updateddepartmentEntity = this.modelMapper.map(departmentDto, DepartmentEntity.class);
        updateddepartmentEntity.setId(departmentId);
        this.departmentRepository.save(updateddepartmentEntity);
        return this.modelMapper.map(updateddepartmentEntity, DepartmentDto.class);

    }
}
