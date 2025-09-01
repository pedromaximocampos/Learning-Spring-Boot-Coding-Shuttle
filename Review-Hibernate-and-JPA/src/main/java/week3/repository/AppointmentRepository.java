package week3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import week3.entities.AppointmentEntity;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
}
