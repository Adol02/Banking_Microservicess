package com.learning.accounts.service.impl;

import com.learning.accounts.constants.AccountsConstants;
import com.learning.accounts.dto.AccountsDto;
import com.learning.accounts.dto.CustomerDto;
import com.learning.accounts.entity.Accounts;
import com.learning.accounts.entity.Customer;
import com.learning.accounts.exceptions.CustomerAlreadyExistsException;
import com.learning.accounts.exceptions.ResourceNotFoundException;
import com.learning.accounts.mapper.AccountsMapper;
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
     * @return Account details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        //rare scenario
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(new CustomerDto(), customer);
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return - boolean indicating if the update of account details is successdful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);
           Long cusotomerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(cusotomerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", cusotomerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - input mobile number
     * @return boolean indicating if the delete of account is succesful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
