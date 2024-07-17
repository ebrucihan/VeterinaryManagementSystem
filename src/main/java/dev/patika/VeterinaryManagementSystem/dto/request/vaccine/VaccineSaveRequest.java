package dev.patika.VeterinaryManagementSystem.dto.request.vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineSaveRequest {

    private String vaccineName;

    private String vaccineCode;

    private LocalDate protectionStartDate;

    private LocalDate protectionFinishDate;

}
