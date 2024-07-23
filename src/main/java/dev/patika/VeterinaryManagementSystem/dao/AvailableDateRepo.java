package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvailableDateRepo extends JpaRepository<AvailableDate,Long> {

    List<AvailableDate> findByDoctor_DoctorId(Long doctorId);
    boolean existsByDoctor_DoctorIdAndDate(Long doctorId, LocalDate date);
}
