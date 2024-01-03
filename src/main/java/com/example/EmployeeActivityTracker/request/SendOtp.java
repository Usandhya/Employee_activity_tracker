package com.example.EmployeeActivityTracker.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class SendOtp{

    private String emailId;

    private  String otp;

}
