package com.learning.accounts.service.impl;

import com.learning.accounts.constants.AccountsConstants;
import com.learning.accounts.dto.CustomerDto;
import com.learning.accounts.entity.Accounts;
import com.learning.accounts.entity.Customer;
import com.learning.accounts.exceptions.CustomerAlreadyExistsException;
import com.learning.accounts.mapper.CustomerMapper;
import com.learning.accounts.repository.AccountsRepository;
import com.learning.accounts.repository.CustomerRepository;
import com.learning.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor //lombok will generate all the arguments constructor
public class AccountsServiceImpl implements IAccountsService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;
    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer= customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already exists withe same mobile number"+customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer); //save method will take the input object, preparing the sql statement, creating connection with the db, executing the statement
        // commiting the transaction closing the connection all those boilerplate codes which we write are taken care of spring data jpa
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     *
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");


        long randomAccNumber = 1000000000L+ new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        return newAccount;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        return null;
    }
}
