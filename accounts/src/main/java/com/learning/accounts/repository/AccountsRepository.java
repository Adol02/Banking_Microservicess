package com.learning.accounts.repository;


import com.learning.accounts.entity.Accounts;
import com.learning.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {


}
