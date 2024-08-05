package com.codingshuttle.springwebtutorial.springwebtutorial.repository;


import com.codingshuttle.springwebtutorial.springwebtutorial.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository  extends JpaRepository<DepartmentEntity, Long> {
}
