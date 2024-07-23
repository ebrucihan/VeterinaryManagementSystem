package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAppointmentService;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.appointment.AppointmentResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final IAppointmentService appointmentService;

    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public AppointmentResponse createAppointment(@RequestBody AppointmentSaveRequest request) {
        return appointmentService.createAppointment(request);
    }

    @GetMapping("/{id}")
    public AppointmentResponse getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }

    @GetMapping
    public List<AppointmentResponse> getAppointments(
            @RequestParam(required = false) Long doctor,
            @RequestParam(required = false) Long animal,
            @RequestParam(required = false) LocalDateTime startDateTime,
            @RequestParam(required = false) LocalDateTime endDateTime) {
        return appointmentService.getAppointments(doctor, animal, startDateTime, endDateTime);
    }

    @PutMapping("/{id}")
    public AppointmentResponse updateAppointment(@PathVariable Long id, @RequestBody AppointmentUpdateRequest request) {
        request.setAppointmentId(id);
        return appointmentService.updateAppointment(request);
    }
}