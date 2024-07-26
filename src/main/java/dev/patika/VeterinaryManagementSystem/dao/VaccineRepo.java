package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for accessing and manipulating Vaccine entities.
 */
@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {

    /**
     * Finds a vaccine by its name where the protection finish date is after the given current date.
     *
     * @param vaccineName The name of the vaccine to search for.
     * @param currentDate The date to check against the vaccine's protection finish date.
     * @return An Optional containing the vaccine if found, otherwise an empty Optional.
     */
    Optional<Vaccine> findByVaccineNameAndProtectionFinishDateAfter(String vaccineName, LocalDate currentDate);

}
