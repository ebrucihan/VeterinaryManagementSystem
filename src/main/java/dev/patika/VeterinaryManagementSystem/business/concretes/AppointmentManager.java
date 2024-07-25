package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAppointmentService;
import dev.patika.VeterinaryManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.exception.ScheduleConflictException;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilies.Msg;
import dev.patika.VeterinaryManagementSystem.dao.AppointmentRepo;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.appointment.AppointmentResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.Appointment;
import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentManager implements IAppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final ModelMapper modelMapper;
    private final DoctorManager doctorManager;
    private final IAvailableDateService availableDateService;

    @Autowired
    public AppointmentManager(AppointmentRepo appointmentRepo, ModelMapper modelMapper, DoctorManager doctorManager, IAvailableDateService availableDateService) {
        this.appointmentRepo = appointmentRepo;
        this.modelMapper = modelMapper;
        this.doctorManager = doctorManager;
        this.availableDateService = availableDateService;
    }

    @Override
    @Transactional(rollbackFor = {ScheduleConflictException.class, RuntimeException.class})
    public ResultData<AppointmentResponse> createAppointment(AppointmentSaveRequest request) {
        Animal animal = new Animal();
        animal.setAnimalId(request.getAnimal());

        Doctor doctor = new Doctor();
        doctor.setDoctorId(request.getDoctor());

        LocalDateTime appointmentDateTime = request.getAppointmentDateTime();
        LocalDate appointmentDate = appointmentDateTime.toLocalDate();  // Extract LocalDate

        // Check if the doctor is available on the requested date
        ResultData<Boolean> availabilityResult = availableDateService.isDoctorAvailable(doctor.getDoctorId(), appointmentDate);
        if (!availabilityResult.getData()) {
            return new ResultData<>(false, Msg.DOCTOR_NOT_AVAILABLE, "409", null);
        }

        // Check for appointment conflict
        if (appointmentRepo.existsByDoctor_DoctorIdAndAppointmentDateTime(doctor.getDoctorId(), appointmentDateTime)) {
            return new ResultData<>(false, Msg.APPOINTMENT_CONFLICT, "409", null);
        }

        // Create and save the appointment
        Appointment appointment = new Appointment();
        appointment.setAnimal(animal);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDateTime(appointmentDateTime);

        appointment = appointmentRepo.save(appointment);

        // Map the response
        AppointmentResponse response = modelMapper.map(appointment, AppointmentResponse.class);

        return new ResultData<>(true, Msg.APPOINTMENT_CREATED, "201", response);
    }

    @Override
    @Transactional(rollbackFor = {ScheduleConflictException.class, RuntimeException.class})
    public ResultData<AppointmentResponse> updateAppointment(AppointmentUpdateRequest request) {
        Appointment appointment = appointmentRepo.findById(request.getAppointmentId())
                .orElseThrow(() -> new NotFoundException(Msg.APPOINTMENTS_NOT_FOUND));

        Animal animal = new Animal();
        animal.setAnimalId(request.getAnimal());

        Doctor doctor = new Doctor();
        doctor.setDoctorId(request.getDoctor());

        LocalDateTime appointmentDateTime = request.getAppointmentDateTime();
        LocalDate appointmentDate = appointmentDateTime.toLocalDate();  // Extract LocalDate

        // Check if the doctor is available on the requested date and time
        // Exclude the current appointment from the conflict check
        ResultData<Boolean> availabilityResult = availableDateService.isDoctorAvailable(doctor.getDoctorId(), appointmentDate);
        if (!availabilityResult.getData()) {
            return new ResultData<>(false, Msg.DOCTOR_NOT_AVAILABLE, "409", null);
        }

        // Check for appointment conflict (excluding the updated appointment)
        if (appointmentRepo.existsByDoctor_DoctorIdAndAppointmentDateTimeAndAppointmentIdNot(
                doctor.getDoctorId(), appointmentDateTime, request.getAppointmentId())) {
            return new ResultData<>(false, Msg.APPOINTMENT_CONFLICT, "409", null);
        }

        // Update the appointment
        appointment.setAnimal(animal);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDateTime(appointmentDateTime);

        appointment = appointmentRepo.save(appointment);

        AppointmentResponse response = modelMapper.map(appointment, AppointmentResponse.class);

        return new ResultData<>(true, Msg.APPOINTMENT_UPDATED, "200", response);
    }

    @Override
    public ResultData<AppointmentResponse> getAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException(Msg.APPOINTMENTS_NOT_FOUND));
        AppointmentResponse response = modelMapper.map(appointment, AppointmentResponse.class);
        return new ResultData<>(true, Msg.OK, "200", response);
    }

    @Override
    public ResultData<Void> deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException(Msg.APPOINTMENTS_NOT_FOUND));
        appointmentRepo.delete(appointment);
        return new ResultData<>(true, Msg.APPOINTMENT_DELETED, "200", null);
    }

    @Override
    public ResultData<List<AppointmentResponse>> getAppointments(Long doctor, Long animal, LocalDateTime startDateTime) {
        List<Appointment> appointments;
        if (doctor != null && startDateTime != null) {
            appointments = appointmentRepo.findByDoctor_DoctorIdAndAppointmentDateTimeGreaterThanEqual(doctor, startDateTime);
        } else if (animal != null && startDateTime != null) {
            appointments = appointmentRepo.findByAnimal_AnimalIdAndAppointmentDateTimeGreaterThanEqual(animal, startDateTime);
        } else {
            appointments = appointmentRepo.findAll();
        }
        List<AppointmentResponse> responses = appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return new ResultData<>(true, Msg.OK, "200", responses);
    }


    @Override
    public ResultData<List<AppointmentResponse>> getAppointmentsByDoctorAndDate(Long doctorId, LocalDateTime startDateTime) {
        if (doctorId == null || startDateTime == null) {
            return new ResultData<>(false, Msg.INVALID_INPUT, "400", null);
        }

        List<Appointment> appointments = appointmentRepo.findByDoctor_DoctorIdAndAppointmentDateTimeAfter(doctorId, startDateTime);
        List<AppointmentResponse> responses = appointments.stream()
                .map(appointment -> {
                    AppointmentResponse response = new AppointmentResponse();
                    response.setAppointmentId(appointment.getAppointmentId());
                    response.setDoctorId(appointment.getDoctor().getDoctorId());
                    response.setAnimalId(appointment.getAnimal().getAnimalId());
                    response.setAppointmentDateTime(appointment.getAppointmentDateTime());
                    return response;
                })
                .collect(Collectors.toList());

        if (responses.isEmpty()) {
            return new ResultData<>(false, Msg.APPOINTMENTS_NOT_FOUND, "404", responses);
        }

        return new ResultData<>(true, Msg.APPOINTMENTS_FETCHED_BY_DOCTOR, "200", responses);
    }

    @Override
    public ResultData<List<AppointmentResponse>> getAppointmentsByAnimalAndDate(Long animalId, LocalDateTime startDateTime) {
        if (animalId == null || startDateTime == null) {
            return new ResultData<>(false, Msg.INVALID_INPUT, "400", null);
        }

        List<Appointment> appointments = appointmentRepo.findByAnimal_AnimalIdAndAppointmentDateTimeAfter(animalId, startDateTime);
        List<AppointmentResponse> responses = appointments.stream()
                .map(appointment -> {
                    AppointmentResponse response = new AppointmentResponse();
                    response.setAppointmentId(appointment.getAppointmentId());
                    response.setDoctorId(appointment.getDoctor().getDoctorId());
                    response.setAnimalId(appointment.getAnimal().getAnimalId());
                    response.setAppointmentDateTime(appointment.getAppointmentDateTime());
                    return response;
                })
                .collect(Collectors.toList());

        if (responses.isEmpty()) {
            return new ResultData<>(false, Msg.APPOINTMENTS_NOT_FOUND, "404", responses);
        }

        return new ResultData<>(true, Msg.APPOINTMENTS_FETCHED_BY_ANIMAL, "200", responses);
    }
}
