package com.example.EmployeeActivityTracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@Table(name="otp-table")
public class SendOtpEntity {

    @Id
    private String emailId;

    private  String otp;

}
