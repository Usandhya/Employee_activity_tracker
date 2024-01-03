package com.example.EmployeeActivityTracker.request;

import lombok.Data;

@Data
public class AdminRequest {

    public String emailId;
    public String password;
    public String otp;
}
