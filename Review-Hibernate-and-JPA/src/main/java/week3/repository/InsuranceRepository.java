package week3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week3.entities.InsuranceEntity;

public interface InsuranceRepository extends JpaRepository<InsuranceEntity, Long> {
}
