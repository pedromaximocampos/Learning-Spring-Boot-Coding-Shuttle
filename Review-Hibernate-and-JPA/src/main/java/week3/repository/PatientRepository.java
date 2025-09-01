package week3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week3.entities.PatientEntity;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
}
