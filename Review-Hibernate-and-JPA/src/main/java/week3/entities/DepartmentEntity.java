package week3.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "departments")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;


    @Column(nullable = false, length = 100, unique = true)
    private String name;


    @OneToOne
    @JoinColumn(name = "head_of_department_id", referencedColumnName = "id", nullable = false)
    private DoctorEntity headOfDepartment;


    @ManyToMany
    private Set<DoctorEntity> doctors;
}
