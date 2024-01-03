package com.example.EmployeeActivityTracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@Table(name="Emp_otp-table")
public class EmployeeSendOtpEntity {

    @Id
    private String emailId;

    private  String otp;

}
