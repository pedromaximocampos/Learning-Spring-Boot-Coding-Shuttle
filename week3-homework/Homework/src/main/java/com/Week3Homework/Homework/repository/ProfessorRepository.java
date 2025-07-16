package com.Week3Homework.Homework.repository;


import com.Week3Homework.Homework.entities.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {

    @Query("SELECT s FROM SubjectEntity s JOIN FETCH s.professor p WHERE p.id = :professorId")
    Optional<ProfessorEntity> findProfessorWithSubjectsById(@Param("professorId") Long professorId);
    // Additional methods specific to Professor can be defined here if needed
}
