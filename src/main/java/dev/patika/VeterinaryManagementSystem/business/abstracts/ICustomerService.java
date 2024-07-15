package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.customer.CustomerResponse;

public interface ICustomerService {

    ResultData<CustomerResponse> addCustomer(CustomerSaveRequest request);
}
