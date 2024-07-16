package dev.patika.VeterinaryManagementSystem.dto.response.customer;


import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private Long customerId;
    private String customerName;
    private String customerPhone;
    private String customerMail;
    private String customerAddress;
    private String customerCity;
}
