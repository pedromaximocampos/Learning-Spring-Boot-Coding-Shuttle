package com.codingShuttle.week3.Jpa.Tutorial.controllers;


import com.codingShuttle.week3.Jpa.Tutorial.dto.ProjectDto;
import com.codingShuttle.week3.Jpa.Tutorial.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        ProjectDto project = projectService.getProjectById(id);

        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        ProjectDto createdProject = projectService.createProject(projectDto);
        return ResponseEntity.status(201).body(createdProject);
    }

    @PutMapping("/{id}/assign-department/{departmentId}")
    public ResponseEntity<ProjectDto> assignDepartmentToProject(@PathVariable Long id,
                                                                @PathVariable Long departmentId,
                                                                @RequestParam(required = false, defaultValue= "false") boolean addDepartmentEmployees) {
        ProjectDto project = projectService.assignDepartmentToProject(id, departmentId, addDepartmentEmployees);
        return ResponseEntity.ok(project);
    }

}
