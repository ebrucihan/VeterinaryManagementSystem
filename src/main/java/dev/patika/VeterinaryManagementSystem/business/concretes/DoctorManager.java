package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.utilies.Msg;
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

    public DoctorManager(DoctorRepo doctorRepo, ModelMapper modelMapper) {
        this.doctorRepo = doctorRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorResponse save(DoctorSaveRequest request) {
        Doctor doctor = modelMapper.map(request, Doctor.class);
        Doctor savedDoctor = doctorRepo.save(doctor);
        DoctorResponse response = modelMapper.map(savedDoctor, DoctorResponse.class);
        response.setMessage(Msg.DOCTOR_CREATED);
        return response;
    }

    @Override
    public DoctorResponse update(long id, DoctorUpdateRequest request) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.DOCTOR_NOT_FOUND));
        modelMapper.map(request, doctor);
        Doctor updatedDoctor = doctorRepo.save(doctor);
        DoctorResponse response = modelMapper.map(updatedDoctor, DoctorResponse.class);
        response.setMessage(Msg.DOCTOR_UPDATED);
        return response;
    }

    @Override
    public DoctorResponse getById(long id) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.DOCTOR_NOT_FOUND));
        DoctorResponse response = modelMapper.map(doctor, DoctorResponse.class);
        response.setMessage(Msg.OK);
        return response;
    }

    @Override
    public List<DoctorResponse> getAll() {
        return doctorRepo.findAll().stream()
                .map(doctor -> {
                    DoctorResponse response = modelMapper.map(doctor, DoctorResponse.class);
                    response.setMessage(Msg.OK);
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.DOCTOR_NOT_FOUND));
        doctorRepo.delete(doctor);
    }

    @Override
    public boolean isDoctorAvailable(long doctorId, LocalDate date) {
        // Implement logic to check if the doctor is available on the given date
        // For simplicity, assume the doctor is always available
        return true;
    }
}