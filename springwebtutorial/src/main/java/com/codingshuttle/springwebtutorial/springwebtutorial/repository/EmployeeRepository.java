package com.codingshuttle.springwebtutorial.springwebtutorial.repository;

import com.codingshuttle.springwebtutorial.springwebtutorial.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}
