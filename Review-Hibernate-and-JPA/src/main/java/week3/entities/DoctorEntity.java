package week3.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "doctors")
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    private String specialty;

    @Column(nullable = false, length = 20, unique = true)
    private String phoneNumber;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
