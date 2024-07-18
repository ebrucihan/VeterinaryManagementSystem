package dev.patika.VeterinaryManagementSystem.dto.request.doctor;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateRequest {

    private String doctorName;
    private String doctorPhone;
    private String doctorMail;
    private String doctorAddress;
    private String doctorCity;


}
