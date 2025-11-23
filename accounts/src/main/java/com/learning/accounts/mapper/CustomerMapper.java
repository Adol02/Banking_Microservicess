package com.learning.accounts.mapper;

import com.learning.accounts.dto.CustomerDto;
import com.learning.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(CustomerDto customerDto, Customer customer){
        customerDto.setEmail(customer.getEmail());
        customerDto.setName(customerDto.getName());
        customerDto.setMobileNumber(customerDto.getMobileNumber());
        return customerDto;
    }
    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer){
        customer.setEmail(customerDto.getEmail());
        customer.setName(customer.getName());
        customer.setMobileNumber(customer.getMobileNumber());
        return customer;
    }
}
