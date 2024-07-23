package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAppointmentService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.exception.ScheduleConflictException;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentManager implements IAppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final ModelMapper modelMapper;
    private final DoctorManager doctorManager;

    @Autowired
    public AppointmentManager(AppointmentRepo appointmentRepo, ModelMapper modelMapper, DoctorManager doctorManager) {
        this.appointmentRepo = appointmentRepo;
        this.modelMapper = modelMapper;
        this.doctorManager = doctorManager;
    }


    @Override
    @Transactional(rollbackFor = {ScheduleConflictException.class, RuntimeException.class})
    public AppointmentResponse createAppointment(AppointmentSaveRequest request) {
        // AppointmentSaveRequest içinden animalId ve doctorId ile ilgili nesneleri çekiyoruz
        Animal animal = new Animal();
        animal.setAnimalId(request.getAnimal());

        Doctor doctor = new Doctor();
        doctor.setDoctorId(request.getDoctor());

        LocalDateTime appointmentDateTime = request.getAppointmentDateTime();

        // Doctor availability check
        if (!doctorManager.isDoctorAvailable(doctor.getDoctorId(), appointmentDateTime.toLocalDate())) {
            throw new ScheduleConflictException(Msg.DOCTOR_NOT_AVAILABLE);
        }

        // Check if there is already an appointment at the given time
        if (appointmentRepo.existsByDoctor_DoctorIdAndAppointmentDateTime(doctor.getDoctorId(), appointmentDateTime)) {
            throw new ScheduleConflictException(Msg.APPOINTMENT_CONFLICT);
        }

        // Create appointment entity
        Appointment appointment = new Appointment();
        appointment.setAnimal(animal);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDateTime(appointmentDateTime);

        // Save appointment to repository
        appointment = appointmentRepo.save(appointment);

        AppointmentResponse response = modelMapper.map(appointment, AppointmentResponse.class);
        response.setMessage(Msg.APPOINTMENT_CREATED); // Set the success message

        return response;
    }

    @Override
    @Transactional(rollbackFor = {ScheduleConflictException.class, RuntimeException.class})
    public AppointmentResponse updateAppointment (AppointmentUpdateRequest request) {
        Appointment appointment = appointmentRepo.findById(request.getAppointmentId())
                .orElseThrow(() -> new NotFoundException(Msg.APPOINTMENT_NOT_FOUND));

        // Güncellenecek hayvan ve doktor nesnelerini çekiyoruz
        Animal animal = new Animal();
        animal.setAnimalId(request.getAnimal());

        Doctor doctor = new Doctor();
        doctor.setDoctorId(request.getDoctor());

        LocalDateTime appointmentDateTime = request.getAppointmentDateTime();

        // Doctor availability check
        if (!doctorManager.isDoctorAvailable(doctor.getDoctorId(), appointmentDateTime.toLocalDate())) {
            throw new ScheduleConflictException(Msg.DOCTOR_NOT_AVAILABLE);
        }

        // Check if there is already an appointment at the given time (excluding current appointment)
        if (appointmentRepo.existsByDoctor_DoctorIdAndAppointmentDateTimeAndAppointmentIdNot(doctor.getDoctorId(), appointmentDateTime, request.getAppointmentId())) {
            throw new ScheduleConflictException(Msg.APPOINTMENT_CONFLICT);
        }

        // Update appointment entity
        appointment.setAnimal(animal);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDateTime(appointmentDateTime);

        // Save updated appointment to repository
        appointment = appointmentRepo.save(appointment);

        AppointmentResponse response = modelMapper.map(appointment, AppointmentResponse.class);
        response.setMessage(Msg.APPOINTMENT_UPDATED); // Set the success message

        return response;
    }

    @Override
    public AppointmentResponse getAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException(Msg.APPOINTMENT_NOT_FOUND));
        return modelMapper.map(appointment, AppointmentResponse.class);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException(Msg.APPOINTMENT_NOT_FOUND));
        appointmentRepo.delete(appointment);
    }

    @Override
    public List<AppointmentResponse> getAppointments(Long doctor, Long animal, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Appointment> appointments;
        if (doctor != null && startDateTime != null && endDateTime != null) {
            appointments = appointmentRepo.findByDoctor_DoctorIdAndAppointmentDateTimeBetween(doctor, startDateTime, endDateTime);
        } else if (animal != null && startDateTime != null && endDateTime != null) {
            appointments = appointmentRepo.findByAnimal_AnimalIdAndAppointmentDateTimeBetween(animal, startDateTime, endDateTime);
        } else {
            appointments = appointmentRepo.findAll();
        }
        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
    }
}