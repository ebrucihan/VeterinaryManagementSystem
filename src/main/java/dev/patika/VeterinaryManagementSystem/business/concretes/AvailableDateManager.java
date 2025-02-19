package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
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
    public ResultData<AvailableDateResponse> addAvailableDate(AvailableDateSaveRequest request) {
        // Find the doctor by ID
        Doctor doctor = doctorRepo.findById(request.getDoctorId())
                .orElseThrow(() -> new NotFoundException(Msg.DOCTOR_NOT_FOUND));

        // Check if the available date already exists for the given doctor
        boolean exists = availableDateRepo.existsByDoctor_DoctorIdAndDate(request.getDoctorId(), request.getDate());
        if (exists) {
            return new ResultData<>(false, Msg.DOCTOR_DATE_ALREADY_EXISTS, "400", null);
        }

        // Create a new AvailableDate object and save it to the database
        AvailableDate availableDate = new AvailableDate();
        availableDate.setDate(request.getDate());
        availableDate.setDoctor(doctor);
        AvailableDate savedAvailableDate = availableDateRepo.save(availableDate);

        // Manually create the response object
        AvailableDateResponse response = new AvailableDateResponse();
        response.setId(savedAvailableDate.getId());
        response.setDoctorId(savedAvailableDate.getDoctor().getDoctorId());
        response.setDate(savedAvailableDate.getDate());

        return new ResultData<>(true, Msg.CREATED, "201", response);
    }

    @Override
    public ResultData<AvailableDateResponse> updateAvailableDate(AvailableDateUpdateRequest request) {
        // Find the existing AvailableDate object by ID
        AvailableDate availableDate = availableDateRepo.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(Msg.AVAILABLE_DATE_NOT_FOUND));

        // Update the date of the AvailableDate object
        availableDate.setDate(request.getDate());
        AvailableDate updatedAvailableDate = availableDateRepo.save(availableDate);

        // Manually create the response object
        AvailableDateResponse response = new AvailableDateResponse();
        response.setId(updatedAvailableDate.getId());
        response.setDoctorId(updatedAvailableDate.getDoctor().getDoctorId());
        response.setDate(updatedAvailableDate.getDate());

        return new ResultData<>(true, Msg.OK, "200", response);
    }

    @Override
    public ResultData<List<AvailableDateResponse>> getAllAvailableDates() {
        // Retrieve all available dates
        List<AvailableDate> availableDates = availableDateRepo.findAll();

        // Convert AvailableDate objects to response objects
        List<AvailableDateResponse> responses = availableDates.stream()
                .map(date -> {
                    AvailableDateResponse response = new AvailableDateResponse();
                    response.setId(date.getId());
                    response.setDoctorId(date.getDoctor().getDoctorId());
                    response.setDate(date.getDate());
                    return response;
                })
                .collect(Collectors.toList());

        return new ResultData<>(true, Msg.OK, "200", responses);
    }

    @Override
    public ResultData<String> deleteAvailableDate(Long id) {
        // Find the AvailableDate object by ID
        AvailableDate availableDate = availableDateRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.AVAILABLE_DATE_NOT_FOUND));

        // Delete the AvailableDate object
        availableDateRepo.delete(availableDate);
        return new ResultData<>(true, Msg.AVAILABLE_DATE_DELETED, "200", "deleted");
    }

    @Override
    public ResultData<List<AvailableDateResponse>> getAvailableDatesByDoctor(Long doctorId) {
        // Retrieve available dates by doctor ID
        List<AvailableDate> availableDates = availableDateRepo.findByDoctor_DoctorId(doctorId);

        // Convert AvailableDate objects to response objects
        List<AvailableDateResponse> responses = availableDates.stream()
                .map(date -> {
                    AvailableDateResponse response = new AvailableDateResponse();
                    response.setId(date.getId());
                    response.setDoctorId(date.getDoctor().getDoctorId());
                    response.setDate(date.getDate());
                    return response;
                })
                .collect(Collectors.toList());

        return new ResultData<>(true, Msg.OK, "200", responses);
    }

    @Override
    public ResultData<Boolean> isDoctorAvailable(long doctorId, LocalDate date) {
        // Check if the doctor is available on the specified date
        boolean isAvailable = availableDateRepo.existsByDoctor_DoctorIdAndDate(doctorId, date);

        if (!isAvailable) {
            // Doctor is not available on the specified date
            return new ResultData<>(false, Msg.DOCTOR_NOT_AVAILABLE, "404", false);
        }

        // Doctor is available on the specified date
        return new ResultData<>(true, Msg.DOCTOR_AVAILABILITY_CHECKED, "200", true);
    }
}
