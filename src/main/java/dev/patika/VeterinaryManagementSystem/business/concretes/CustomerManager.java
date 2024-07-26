package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VeterinaryManagementSystem.core.config.ModelMapper.IModelMapperService;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilies.Msg;
import dev.patika.VeterinaryManagementSystem.core.utilies.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dao.CustomerRepo;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.animal.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.dto.response.customer.CustomerResponse;
import dev.patika.VeterinaryManagementSystem.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements ICustomerService {

    private final CustomerRepo customerRepo;
    private final IModelMapperService modelMapperService;

    @Autowired
    public CustomerManager(CustomerRepo customerRepo, IModelMapperService modelMapperService) {
        this.customerRepo = customerRepo;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public ResultData<CustomerResponse> addCustomer(CustomerSaveRequest request) {
        // Map the request DTO to the Customer entity
        Customer customer = modelMapperService.forRequest().map(request, Customer.class);

        // Save the Customer entity to the database
        Customer savedCustomer = customerRepo.save(customer);

        // Map the saved Customer entity to the response DTO
        CustomerResponse response = modelMapperService.forResponse().map(savedCustomer, CustomerResponse.class);

        // Return a successful result with the created customer response
        return ResultHelper.created(response, Msg.CUSTOMER_CREATED);
    }

    @Override
    public ResultData<CustomerResponse> updateCustomer(long customerId, CustomerUpdateRequest request) {
        // Find the Customer entity by ID
        Optional<Customer> optionalCustomer = customerRepo.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            throw new NotFoundException("Customer not found with ID: " + customerId);
        }
        Customer existingCustomer = optionalCustomer.get();

        // Update the Customer entity with the provided request data
        if (request.getCustomerName() != null) {
            existingCustomer.setCustomerName(request.getCustomerName());
        }
        if (request.getCustomerPhone() != null) {
            existingCustomer.setCustomerPhone(request.getCustomerPhone());
        }
        if (request.getCustomerMail() != null) {
            existingCustomer.setCustomerMail(request.getCustomerMail());
        }
        if (request.getCustomerAddress() != null) {
            existingCustomer.setCustomerAddress(request.getCustomerAddress());
        }
        if (request.getCustomerCity() != null) {
            existingCustomer.setCustomerCity(request.getCustomerCity());
        }

        // Save the updated Customer entity
        Customer updatedCustomer = customerRepo.save(existingCustomer);

        // Map the updated Customer entity to the response DTO
        CustomerResponse response = modelMapperService.forResponse().map(updatedCustomer, CustomerResponse.class);

        // Return a successful result with the updated customer response
        return new ResultData<>(true, Msg.CUSTOMER_UPDATED, "200", response);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        // Retrieve all Customer entities from the database
        List<Customer> customers = customerRepo.findAll();

        // Map each Customer entity to the response DTO
        return customers.stream()
                .map(customer -> modelMapperService.forResponse().map(customer, CustomerResponse.class))
                .toList();
    }

    @Override
    public CustomerResponse getCustomerById(long customerId) {
        // Find the Customer entity by ID
        Optional<Customer> optionalCustomer = customerRepo.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            throw new NotFoundException("Customer not found with ID: " + customerId);
        }

        // Map the found Customer entity to the response DTO
        return modelMapperService.forResponse().map(optionalCustomer.get(), CustomerResponse.class);
    }

    @Override
    public ResultData<String> deleteCustomerById(long customerId) {
        // Check if the Customer entity exists by ID
        if (!customerRepo.existsById(customerId)) {
            throw new NotFoundException(Msg.CUSTOMER_NOT_FOUND + " with ID: " + customerId);
        }

        // Delete the Customer entity by ID
        customerRepo.deleteById(customerId);

        // Return a successful result with a message indicating the customer was deleted
        return new ResultData<>(true, Msg.CUSTOMER_DELETED, "200", "Customer ID: " + customerId);
    }

    @Override
    public List<CustomerResponse> getCustomersByName(String customerName) {
        // Find customers by name containing the specified string (case insensitive)
        List<Customer> customers = customerRepo.findByCustomerNameContainingIgnoreCase(customerName);
        if (customers.isEmpty()) {
            throw new NotFoundException("Customer not found with name containing: " + customerName);
        }

        // Map each found Customer entity to the response DTO
        return customers.stream()
                .map(customer -> modelMapperService.forResponse().map(customer, CustomerResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalResponse> getAnimalsByCustomerId(Long customerId) {
        // Retrieve animals associated with the specified customer ID
        return customerRepo.getAnimalsByCustomerId(customerId);
    }
}
