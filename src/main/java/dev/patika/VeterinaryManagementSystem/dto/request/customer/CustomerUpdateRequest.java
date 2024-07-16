package dev.patika.VeterinaryManagementSystem.dto.request.customer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String customerName;

    @NotBlank(message = "Phone is mandatory")
    @Pattern(regexp = "^\\+?[0-9]{10,13}$", message = "Phone number should be valid")
    private String customerPhone;

    @Email(message = "Email should be valid")
    private String customerMail;

    @Size(max = 100, message = "Address should be up to 100 characters")
    private String customerAddress;

    @NotBlank(message = "City is mandatory")
    @Size(max = 50, message = "City should be up to 50 characters")
    private String customerCity;

}
