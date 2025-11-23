package com.learning.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor @Data
public class ErrorResponseDto {

    private String apiPath; //api invoked

    private HttpStatus errorCode; //sharing what is the reason for the error

    private String errorMessage;

    private LocalDateTime errorTime; //what time error happens helps developer to debug the log amd error systems

}
