package com.codingShuttle.week3.Jpa.Tutorial.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "employees"
)
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(mappedBy = "manager")
    private DepartmentEntity managedDepartment;


    @ManyToOne
    @JoinColumn(name = "working_department_id", referencedColumnName = "id")
    private DepartmentEntity workingDepartment;

    @ManyToMany(mappedBy = "members")
    private Set<ProjectEntity> projectsParticipating = new HashSet<>();


}
