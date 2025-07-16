package com.Week3Homework.Homework.repository;


import com.Week3Homework.Homework.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

    // Additional methods specific to Subject can be defined here if needed

    @Query("""
        SELECT s FROM SubjectEntity s\s
        JOIN FETCH s.professor p\s
        WHERE p.id = :professorId
        ORDER BY s.title
       \s""")
    List<SubjectEntity> findSubjectsWithProfessorById(@Param("professorId") Long professorId);
}
