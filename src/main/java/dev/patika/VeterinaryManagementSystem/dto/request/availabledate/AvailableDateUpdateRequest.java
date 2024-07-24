package dev.patika.VeterinaryManagementSystem.dto.request.availabledate;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailableDateUpdateRequest {
    private Long id; // Güncellenecek tarih için ID
    private LocalDate date;
}
