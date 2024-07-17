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
        Customer customer = modelMapperService.forRequest().map(request, Customer.class);
        Customer savedCustomer = customerRepo.save(customer);
        CustomerResponse response = modelMapperService.forResponse().map(savedCustomer, CustomerResponse.class);
        return ResultHelper.created(response, Msg.VACCINE_CREATED);
    }

    @Override
    public ResultData<CustomerResponse> updateCustomer(long customerId, CustomerUpdateRequest request) {
        Optional<Customer> optionalCustomer = customerRepo.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            throw new NotFoundException("Customer not found with ID: " + customerId);
        }
        Customer existingCustomer = optionalCustomer.get();

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

        Customer updatedCustomer = customerRepo.save(existingCustomer);
        CustomerResponse response = modelMapperService.forResponse().map(updatedCustomer, CustomerResponse.class);
        return new ResultData<>(true, Msg.CUSTOMER_UPDATED, "200", response);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return customers.stream()
                .map(customer -> modelMapperService.forResponse().map(customer, CustomerResponse.class))
                .toList();
    }

    @Override
    public CustomerResponse getCustomerById(long customerId) {
        Optional<Customer> optionalCustomer = customerRepo.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            throw new NotFoundException("Customer not found with ID: " + customerId);
        }
        return modelMapperService.forResponse().map(optionalCustomer.get(), CustomerResponse.class);
    }

    @Override
    public void deleteCustomerById(long customerId) {
        if (!customerRepo.existsById(customerId)) {
            throw new NotFoundException(Msg.CUSTOMER_NOT_FOUND + " with ID: " + customerId);
        }
        customerRepo.deleteById(customerId);
        throw new NotFoundException(Msg.CUSTOMER_DELETED + " with ID: " + customerId);

    }

    @Override
    public List<CustomerResponse> getCustomersByName(String customerName) {
        List<Customer> customers = customerRepo.findByCustomerNameContainingIgnoreCase(customerName);
        if (customers.isEmpty()) {
            throw new NotFoundException("Customer not found with name containing: " + customerName);
        }
        return customers.stream()
                .map(customer -> modelMapperService.forResponse().map(customer, CustomerResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalResponse> getAnimalsByCustomerId(Long customerId) {
        return customerRepo.getAnimalsByCustomerId(customerId);
    }
}

