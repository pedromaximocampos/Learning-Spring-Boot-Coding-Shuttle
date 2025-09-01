package week3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week3.entities.DepartmentEntity;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
