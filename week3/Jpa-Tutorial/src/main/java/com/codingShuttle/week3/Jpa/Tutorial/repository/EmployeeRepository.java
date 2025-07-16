package com.codingShuttle.week3.Jpa.Tutorial.repository;


import com.codingShuttle.week3.Jpa.Tutorial.dto.EmployeeDto;
import com.codingShuttle.week3.Jpa.Tutorial.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    // Additional methods specific to Employee can be defined here if needed
}
