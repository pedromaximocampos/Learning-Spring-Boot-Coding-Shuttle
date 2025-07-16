package com.Week3Homework.Homework.repository;


import com.Week3Homework.Homework.entities.AdmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdmissionRepository extends JpaRepository<AdmissionEntity, Long> {

    @Query("SELECT a FROM AdmissionEntity a JOIN FETCH a.student")
    Optional<List<AdmissionEntity>> findAllAdmissionsWithStudents();

    @Query("SELECT a FROM AdmissionEntity a JOIN FETCH a.student ORDER BY a.student.name")
    Optional<List<AdmissionEntity>> findAllAdmissionsWithStudentsOrderedByStudentName();


    @Query("SELECT a FROM AdmissionEntity a JOIN FETCH a.student WHERE a.id = :admissionId")
    Optional<AdmissionEntity> findAdmissionWithStudentById(@Param("admissionId") Long admissionId);
}
