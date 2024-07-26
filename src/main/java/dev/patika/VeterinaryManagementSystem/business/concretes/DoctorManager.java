package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilies.Msg;
import dev.patika.VeterinaryManagementSystem.dao.AppointmentRepo;
import dev.patika.VeterinaryManagementSystem.dao.DoctorRepo;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.doctor.DoctorResponse;
import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorManager implements IDoctorService {

    private final DoctorRepo doctorRepo;
    private final ModelMapper modelMapper;
    private final AppointmentRepo appointmentRepo;

    public DoctorManager(DoctorRepo doctorRepo, ModelMapper modelMapper, AppointmentRepo appointmentRepo) {
        this.doctorRepo = doctorRepo;
        this.modelMapper = modelMapper;
        this.appointmentRepo = appointmentRepo;
    }

    @Override
    public ResultData<DoctorResponse> save(DoctorSaveRequest request) {
        // Map the DoctorSaveRequest DTO to a Doctor entity
        Doctor doctor = modelMapper.map(request, Doctor.class);

        // Save the Doctor entity to the database
        Doctor savedDoctor = doctorRepo.save(doctor);

        // Map the saved Doctor entity to a DoctorResponse DTO
        DoctorResponse response = modelMapper.map(savedDoctor, DoctorResponse.class);

        // Return a successful result with the created DoctorResponse
        return new ResultData<>(true, Msg.DOCTOR_CREATED, "200", response);
    }

    @Override
    public ResultData<DoctorResponse> update(long id, DoctorUpdateRequest request) {
        // Find the Doctor entity by ID
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.DOCTOR_NOT_FOUND));

        // Map the DoctorUpdateRequest DTO to the existing Doctor entity
        modelMapper.map(request, doctor);

        // Save the updated Doctor entity to the database
        Doctor updatedDoctor = doctorRepo.save(doctor);

        // Map the updated Doctor entity to a DoctorResponse DTO
        DoctorResponse response = modelMapper.map(updatedDoctor, DoctorResponse.class);

        // Return a successful result with the updated DoctorResponse
        return new ResultData<>(true, Msg.DOCTOR_UPDATED, "200", response);
    }

    @Override
    public ResultData<DoctorResponse> getById(long id) {
        // Find the Doctor entity by ID
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.DOCTOR_NOT_FOUND));

        // Map the found Doctor entity to a DoctorResponse DTO
        DoctorResponse response = modelMapper.map(doctor, DoctorResponse.class);

        // Return a successful result with the DoctorResponse
        return new ResultData<>(true, Msg.OK, "200", response);
    }

    @Override
    public ResultData<List<DoctorResponse>> getAll() {
        // Retrieve all Doctor entities from the database
        List<DoctorResponse> responses = doctorRepo.findAll().stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponse.class))
                .collect(Collectors.toList());

        // Return a successful result with the list of DoctorResponse DTOs
        return new ResultData<>(true, Msg.OK, "200", responses);
    }

    @Override
    public ResultData<Void> delete(long id) {
        // Find the Doctor entity by ID
        Doctor doctor = doctorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.DOCTOR_NOT_FOUND));

        // Delete the Doctor entity from the database
        doctorRepo.delete(doctor);

        // Return a successful result with a message indicating the Doctor was deleted
        return new ResultData<>(true, Msg.DOCTOR_DELETED, "200", null);
    }

    @Override
    public ResultData<Boolean> isDoctorAvailable(long doctorId, LocalDate date) {
        // Check if the Doctor exists by ID
        boolean doctorExists = doctorRepo.existsById(doctorId);

        // Check if the Doctor has any appointments on the specified date
        boolean hasAppointments = appointmentRepo.existsByDoctor_DoctorIdAndAppointmentDateTimeBetween(
                doctorId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());

        // Determine if the Doctor is available (exists and has no appointments)
        boolean isAvailable = doctorExists && !hasAppointments;

        // Return a successful result with the availability status
        return new ResultData<>(true, Msg.DOCTOR_AVAILABILITY_CHECKED, "200", isAvailable);
    }
}
