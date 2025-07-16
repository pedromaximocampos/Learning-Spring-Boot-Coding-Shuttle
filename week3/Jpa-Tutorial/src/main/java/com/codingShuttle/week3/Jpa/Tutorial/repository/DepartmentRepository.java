package com.codingShuttle.week3.Jpa.Tutorial.repository;


import com.codingShuttle.week3.Jpa.Tutorial.dto.DepartmentDto;
import com.codingShuttle.week3.Jpa.Tutorial.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    // Additional methods specific to Department can be defined here if needed
}
