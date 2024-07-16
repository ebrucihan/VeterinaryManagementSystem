package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long>{

    List<Customer> findByCustomerNameContainingIgnoreCase(String customerName);


    @Query("SELECT new dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse(a.animalId, a.animalName, a.animalSpecies, a.animalBreed, a.animalGender, a.animalColour, a.animalDateOfBirth, a.customer.customerId) FROM Animal a WHERE a.customer.customerId = :customerId")
    List<AnimalResponse> getAnimalsByCustomerId(@Param("customerId") Long customerId);




}
