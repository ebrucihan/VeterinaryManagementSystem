package dev.patika.VeterinaryManagementSystem.dto.response.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DoctorResponse {
    private long doctorId;
    private String doctorName;
    private String doctorPhone;
    private String doctorMail;
    private String doctorAddress;
    private String doctorCity;

}
