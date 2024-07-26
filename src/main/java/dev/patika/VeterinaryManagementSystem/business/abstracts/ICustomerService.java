package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.dto.response.customer.CustomerResponse;

import java.util.List;

public interface ICustomerService {

    // Add a new customer
    ResultData<CustomerResponse> addCustomer(CustomerSaveRequest request);

    // Update an existing customer by their ID
    ResultData<CustomerResponse> updateCustomer(long customerId, CustomerUpdateRequest request);

    // Retrieve a list of all customers
    List<CustomerResponse> getAllCustomers();

    // Retrieve a specific customer by their ID
    CustomerResponse getCustomerById(long customerId);

    // Delete a customer by their ID
    ResultData<String> deleteCustomerById(long customerId);

    // Search for customers by their name
    List<CustomerResponse> getCustomersByName(String customerName);

    // Retrieve a list of animals associated with a specific customer
    List<AnimalResponse> getAnimalsByCustomerId(Long customerId);
}
