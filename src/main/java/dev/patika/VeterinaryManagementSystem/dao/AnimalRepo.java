package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and manipulating Animal entities.
 */
@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {

    /**
     * Finds a list of animals where the animal's name contains the given string, ignoring case.
     *
     * @param animalName The string to search for within the animal names.
     * @return A list of animals with names containing the given string.
     */
    List<Animal> findByAnimalNameContainingIgnoreCase(String animalName);

    /**
     * Checks if an animal with the given name exists for a specific customer.
     *
     * @param animalName The name of the animal to check for.
     * @param customerId The ID of the customer to check for.
     * @return True if an animal with the given name exists for the specified customer, otherwise false.
     */
    boolean existsByAnimalNameAndCustomer_CustomerId(String animalName, Long customerId);

}
