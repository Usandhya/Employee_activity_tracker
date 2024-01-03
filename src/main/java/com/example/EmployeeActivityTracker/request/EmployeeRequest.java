package com.example.EmployeeActivityTracker.request;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeRequest {

    private String empID;
    private String firstName;
    private String lastName;
    private String skillSet;
    private String technology;
    private String reportingManager;
    private String doj;
    private String role;
    private String emailId;
    private String password;
}
