package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and manipulating Customer entities.
 */
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    /**
     * Finds a list of customers whose names contain the given string, ignoring case.
     *
     * @param customerName The string to search for within the customer names.
     * @return A list of customers with names containing the given string.
     */
    List<Customer> findByCustomerNameContainingIgnoreCase(String customerName);

    /**
     * Retrieves a list of AnimalResponse DTOs for animals associated with a specific customer ID.
     * Uses a custom query to map Animal entities to AnimalResponse DTOs.
     *
     * @param customerId The ID of the customer to retrieve animals for.
     * @return A list of AnimalResponse DTOs for animals associated with the specified customer ID.
     */
    @Query("SELECT new dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse(a.animalId, a.animalName, a.animalSpecies, a.animalBreed, a.animalGender, a.animalColour, a.animalDateOfBirth, a.customer.customerId) FROM Animal a WHERE a.customer.customerId = :customerId")
    List<AnimalResponse> getAnimalsByCustomerId(@Param("customerId") Long customerId);

}
