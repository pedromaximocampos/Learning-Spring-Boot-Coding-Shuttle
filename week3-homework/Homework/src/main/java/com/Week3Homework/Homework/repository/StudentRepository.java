package com.Week3Homework.Homework.repository;


import com.Week3Homework.Homework.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    // Additional methods specific to Student can be defined here if needed
}
