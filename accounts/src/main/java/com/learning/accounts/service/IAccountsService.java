package com.learning.accounts.service;

import com.learning.accounts.dto.CustomerDto;

public interface IAccountsService {
    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - Input
     * @return - AccountDetails based on the mobile Number
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     *
     * @param customerDto - CustomerDto Object
     * @return - boolean indicating if the update of account details is successdful or not
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - input mobile number
     * @return boolean indicating if the delete of account is succesful or not
     */
    boolean deleteAccount(String mobileNumber);

}
