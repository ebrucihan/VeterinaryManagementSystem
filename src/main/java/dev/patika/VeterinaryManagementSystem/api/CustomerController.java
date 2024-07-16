package dev.patika.VeterinaryManagementSystem.api;


import dev.patika.VeterinaryManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.dto.response.customer.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {


    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResultData<CustomerResponse> addCustomer(@Valid @RequestBody CustomerSaveRequest request) {
        return customerService.addCustomer(request);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<ResultData<CustomerResponse>> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerUpdateRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, request));
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<CustomerResponse> searchCustomersByName(@RequestParam String customerName) {
        return customerService.getCustomersByName(customerName);
    }

    @GetMapping("/customers/{customerId}/animals")
    public List<AnimalResponse> getAnimalsByCustomerId(@PathVariable("customerId") Long customerId) {
        return customerService.getAnimalsByCustomerId(customerId);
    }


}
