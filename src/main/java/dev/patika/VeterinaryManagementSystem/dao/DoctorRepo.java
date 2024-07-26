package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and manipulating Doctor entities.
 */
@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    // No additional query methods are defined in this repository interface.
}
