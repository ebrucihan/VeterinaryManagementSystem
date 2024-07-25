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
        Doctor doctor = modelMapper.map(request, Doctor.class);
        Doctor savedDoctor = doctorRepo.save(doctor);
        DoctorResponse response = modelMapper.map(savedDoctor, DoctorResponse.class);
        return new ResultData<>(true, Msg.DOCTOR_CREATED, "200", response);
    }

    @Override
    public ResultData<DoctorResponse> update(long id, DoctorUpdateRequest request) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.DOCTOR_NOT_FOUND));
        modelMapper.map(request, doctor);
        Doctor updatedDoctor = doctorRepo.save(doctor);
        DoctorResponse response = modelMapper.map(updatedDoctor, DoctorResponse.class);
        return new ResultData<>(true, Msg.DOCTOR_UPDATED, "200", response);
    }

    @Override
    public ResultData<DoctorResponse> getById(long id) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.DOCTOR_NOT_FOUND));
        DoctorResponse response = modelMapper.map(doctor, DoctorResponse.class);
        return new ResultData<>(true, Msg.OK, "200", response);
    }

    @Override
    public ResultData<List<DoctorResponse>> getAll() {
        List<DoctorResponse> responses = doctorRepo.findAll().stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponse.class))
                .collect(Collectors.toList());
        return new ResultData<>(true, Msg.OK, "200", responses);
    }

    @Override
    public ResultData<Void> delete(long id) {
        Doctor doctor = doctorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.DOCTOR_NOT_FOUND));

        doctorRepo.delete(doctor);

        // Mesajın gönderildiğinden emin olun
        return new ResultData<>(true, Msg.DOCTOR_DELETED, "200", null);
    }

    @Override
    public ResultData<Boolean> isDoctorAvailable(long doctorId, LocalDate date) {
        boolean doctorExists = doctorRepo.existsById(doctorId);

        // Belirtilen tarihte doktorun mevcut randevularını kontrol et
        boolean hasAppointments = appointmentRepo.existsByDoctor_DoctorIdAndAppointmentDateTimeBetween(
                doctorId, date.atStartOfDay(), date.plusDays(1).atStartOfDay());

        boolean isAvailable = doctorExists && !hasAppointments;

        return new ResultData<>(true, Msg.DOCTOR_AVAILABILITY_CHECKED, "200", isAvailable);
    }
}