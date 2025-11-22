package com.learning.accounts.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Accounts extends BaseEntity{

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) why no generate, account number is generated with some method we are going to write
    //because account number should not simple as 1 2 3 4
    @Column(name = "account_number")
    private Long accountNumber;


    @Column(name = "customer_id")
    private Long customerId;


    @Column(name = "account_type")
    private String accountType;

    @Column(name = "branch_address")
    private String branchAddress;

}
