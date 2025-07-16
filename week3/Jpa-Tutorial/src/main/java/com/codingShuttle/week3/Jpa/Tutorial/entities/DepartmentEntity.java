package com.codingShuttle.week3.Jpa.Tutorial.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "departments"
)
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @OneToOne
    private EmployeeEntity manager;

    @OneToMany(mappedBy = "workingDepartment")
    @JsonIgnore
    private Set<EmployeeEntity> employees;

    @OneToMany(mappedBy= "departmentResponsible")
    private Set<ProjectEntity> projects;

}
