package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VeterinaryManagementSystem.core.config.ModelMapper.IModelMapperService;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilies.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dao.CustomerRepo;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.customer.CustomerResponse;
import dev.patika.VeterinaryManagementSystem.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return ResultHelper.created(response);
    }
}

