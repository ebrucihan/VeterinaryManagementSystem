package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.dto.response.animalvaccine.AnimalVaccineResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.AnimalVaccine;
import dev.patika.VeterinaryManagementSystem.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AnimalVaccineRepo extends JpaRepository<AnimalVaccine,Long> {

    boolean existsByAnimalAndVaccine(Animal animal, Vaccine vaccine);
    List<AnimalVaccine> getAnimalVaccinesByAnimal_AnimalId(Long animalId);
    List<AnimalVaccine> findByVaccine_ProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate);




}
