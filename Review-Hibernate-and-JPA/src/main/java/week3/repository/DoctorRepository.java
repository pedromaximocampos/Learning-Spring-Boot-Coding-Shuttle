package week3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week3.entities.DoctorEntity;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
}
