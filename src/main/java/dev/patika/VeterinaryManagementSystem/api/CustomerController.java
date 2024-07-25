package dev.patika.VeterinaryManagementSystem.api;


import dev.patika.VeterinaryManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.dto.response.customer.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResultData<CustomerResponse>> addCustomer(@Valid @RequestBody CustomerSaveRequest request) {
        ResultData<CustomerResponse> result = customerService.addCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);  // HTTP 201 Created
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<ResultData<CustomerResponse>> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerUpdateRequest request) {
        ResultData<CustomerResponse> result = customerService.updateCustomer(customerId, request);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> result = customerService.getAllCustomers();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long customerId) {
        CustomerResponse result = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<ResultData<String>> deleteCustomer(@PathVariable Long customerId) {
        ResultData<String> result = customerService.deleteCustomerById(customerId);
        HttpStatus status = HttpStatus.resolve(Integer.parseInt(result.getCode()));
        return ResponseEntity.status(status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponse>> searchCustomersByName(@RequestParam String customerName) {
        List<CustomerResponse> result = customerService.getCustomersByName(customerName);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{customerId}/animals")
    public ResponseEntity<List<AnimalResponse>> getAnimalsByCustomerId(@PathVariable Long customerId) {
        List<AnimalResponse> result = customerService.getAnimalsByCustomerId(customerId);
        return ResponseEntity.ok(result);
    }
}