package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.dto.response.animalvaccine.AnimalVaccineResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.AnimalVaccine;
import dev.patika.VeterinaryManagementSystem.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for accessing and manipulating AnimalVaccine entities.
 */
@Repository
public interface AnimalVaccineRepo extends JpaRepository<AnimalVaccine, Long> {

    /**
     * Checks if an association between a specific animal and vaccine exists.
     *
     * @param animal The animal entity to check for.
     * @param vaccine The vaccine entity to check for.
     * @return True if an association exists between the given animal and vaccine, otherwise false.
     */
    boolean existsByAnimalAndVaccine(Animal animal, Vaccine vaccine);

    /**
     * Retrieves a list of AnimalVaccine entities associated with a specific animal ID.
     *
     * @param animalId The ID of the animal to retrieve vaccine associations for.
     * @return A list of AnimalVaccine entities associated with the given animal ID.
     */
    List<AnimalVaccine> getAnimalVaccinesByAnimal_AnimalId(Long animalId);

    /**
     * Finds a list of AnimalVaccine entities where the vaccine's protection finish date falls within the given date range.
     *
     * @param startDate The start date of the protection finish date range.
     * @param endDate The end date of the protection finish date range.
     * @return A list of AnimalVaccine entities with protection finish dates between the given start and end dates.
     */
    List<AnimalVaccine> findByVaccine_ProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate);

}
