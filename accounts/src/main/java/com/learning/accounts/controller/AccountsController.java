package com.learning.accounts.controller;


import com.learning.accounts.constants.AccountsConstants;
import com.learning.accounts.dto.CustomerDto;
import com.learning.accounts.dto.ResponseDto;
import com.learning.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountsController {

    private IAccountsService iAccountsService;



    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        //if any exception is thrown it won't come down it will go to the global exception handling
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

    }
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber){
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDto customerDto){
        boolean idUpdated = iAccountsService.updateAccount(customerDto);
        if(idUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.STATUS_500));
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(){

        return null;
    }





}


//    @GetMapping("sayHello")
//    public String sayHello(){
//        return " hi world ...";
//    }