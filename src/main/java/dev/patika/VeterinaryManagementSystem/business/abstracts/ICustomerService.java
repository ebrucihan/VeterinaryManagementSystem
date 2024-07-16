package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.dto.response.customer.CustomerResponse;

import java.util.List;

public interface ICustomerService {

    ResultData<CustomerResponse> addCustomer(CustomerSaveRequest request);

    ResultData<CustomerResponse> updateCustomer(long customerId, CustomerUpdateRequest request);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(long customerId);

    void deleteCustomerById(long customerId);

    List<CustomerResponse> getCustomersByName(String customerName);

    List<AnimalResponse> getAnimalsByCustomerId(Long customerId);



}
