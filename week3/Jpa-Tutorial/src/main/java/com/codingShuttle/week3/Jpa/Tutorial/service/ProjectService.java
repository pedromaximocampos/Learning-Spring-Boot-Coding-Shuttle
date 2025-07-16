package com.codingShuttle.week3.Jpa.Tutorial.service;


import com.codingShuttle.week3.Jpa.Tutorial.dto.ProjectDto;
import com.codingShuttle.week3.Jpa.Tutorial.entities.DepartmentEntity;
import com.codingShuttle.week3.Jpa.Tutorial.entities.EmployeeEntity;
import com.codingShuttle.week3.Jpa.Tutorial.entities.ProjectEntity;
import com.codingShuttle.week3.Jpa.Tutorial.repository.DepartmentRepository;
import com.codingShuttle.week3.Jpa.Tutorial.repository.EmployeeRepository;
import com.codingShuttle.week3.Jpa.Tutorial.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProjectService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public ProjectService(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper modelMapper, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.departmentRepository = departmentRepository;
    }

    public ProjectDto getProjectById(Long id) {
        ProjectEntity searchedProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project with id " + id + " not found"));

        return modelMapper.map(searchedProject, ProjectDto.class);
    }

    public ProjectDto createProject(ProjectDto projectDto) {
        ProjectEntity projectEntity = modelMapper.map(projectDto, ProjectEntity.class);
        ProjectEntity savedProject = projectRepository.save(projectEntity);
        return modelMapper.map(savedProject, ProjectDto.class);
    }

    public ProjectDto assignDepartmentToProject(Long projectId, Long departmentId, boolean addDepartmentEmployees) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project with id " + projectId + " not found"));


        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department with id " + departmentId + " not found"));

        projectEntity.setDepartmentResponsible(departmentEntity);

        if (addDepartmentEmployees) {
            for (EmployeeEntity employee : departmentEntity.getEmployees()) {
                projectEntity.getMembers().add(employee);
            }
        }

        ProjectEntity updatedProject = projectRepository.save(projectEntity);

        return modelMapper.map(updatedProject, ProjectDto.class);
    }
}
