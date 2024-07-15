package dev.patika.VeterinaryManagementSystem.dto.response.customer;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private long customerId;
    private String customerName;
    private String customerPhone;
    private String customerMail;
    private String customerAddress;
    private String customerCity;
}
