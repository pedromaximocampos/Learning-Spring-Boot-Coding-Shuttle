package com.codingShuttle.week3.Jpa.Tutorial.repository;


import com.codingShuttle.week3.Jpa.Tutorial.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    // Additional methods specific to Project can be defined here if needed
}
