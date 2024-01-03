package com.example.EmployeeActivityTracker.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EmployeeSendOtp {

    private String emailId;

    private  String otp;

}
