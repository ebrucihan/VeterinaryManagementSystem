package dev.patika.VeterinaryManagementSystem.business.concretes;


import dev.patika.VeterinaryManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.utilies.Msg;
import dev.patika.VeterinaryManagementSystem.dao.AvailableDateRepo;
import dev.patika.VeterinaryManagementSystem.dao.DoctorRepo;
import dev.patika.VeterinaryManagementSystem.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.availabledate.AvailableDateUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.availabledate.AvailableDateResponse;
import dev.patika.VeterinaryManagementSystem.entities.AvailableDate;
import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailableDateManager implements IAvailableDateService {

    private final AvailableDateRepo availableDateRepo;
    private final DoctorRepo doctorRepo;
    private final ModelMapper modelMapper;

    public AvailableDateManager(AvailableDateRepo availableDateRepo, DoctorRepo doctorRepo, ModelMapper modelMapper) {
        this.availableDateRepo = availableDateRepo;
        this.doctorRepo = doctorRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public AvailableDateResponse addAvailableDate(AvailableDateSaveRequest request) {
        Doctor doctor = doctorRepo.findById(request.getDoctorId())
                .orElseThrow(() -> new NotFoundException(Msg.DOCTOR_NOT_FOUND));
        AvailableDate availableDate = modelMapper.map(request, AvailableDate.class);
        availableDate.setDoctor(doctor);
        AvailableDate savedAvailableDate = availableDateRepo.save(availableDate);
        return modelMapper.map(savedAvailableDate, AvailableDateResponse.class);
    }

    @Override
    public AvailableDateResponse updateAvailableDate(AvailableDateUpdateRequest request) {
        AvailableDate availableDate = availableDateRepo.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(Msg.AVAILABLE_DATE_NOT_FOUND));
        availableDate.setDate(request.getDate());
        AvailableDate updatedAvailableDate = availableDateRepo.save(availableDate);
        return modelMapper.map(updatedAvailableDate, AvailableDateResponse.class);
    }

    @Override
    public List<AvailableDateResponse> getAvailableDatesByDoctor(Long doctorId) {
        List<AvailableDate> availableDates = availableDateRepo.findByDoctor_DoctorId(doctorId);
        return availableDates.stream()
                .map(date -> modelMapper.map(date, AvailableDateResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailableDateResponse> getAllAvailableDates() { // Yeni yöntem
        List<AvailableDate> availableDates = availableDateRepo.findAll();
        return availableDates.stream()
                .map(date -> modelMapper.map(date, AvailableDateResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isDoctorAvailable(Long doctorId, LocalDate date) {
        boolean exists = availableDateRepo.existsByDoctor_DoctorIdAndDate(doctorId, date);
        System.out.println("Doctor ID: " + doctorId + ", Date: " + date + ", Exists: " + exists + ", Available: " + !exists);
        return !exists; // Eğer kayıt varsa, doktor müsait değil
    }
}
