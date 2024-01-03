package com.example.EmployeeActivityTracker.request;

import lombok.Data;

@Data
public class EmployeeLogin {

    private Integer id;
    private String emailId;
    private String password;
    private String otp;
    private String role;

}
