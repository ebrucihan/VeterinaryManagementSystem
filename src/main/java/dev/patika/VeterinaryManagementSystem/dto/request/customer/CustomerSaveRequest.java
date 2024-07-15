package dev.patika.VeterinaryManagementSystem.dto.request.customer;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerSaveRequest {

    @NotBlank
    private String customerName;

    @NotBlank
    private String customerPhone;

    private String customerMail;

    private String customerAddress;

    @NotBlank
    private String customerCity;
}


