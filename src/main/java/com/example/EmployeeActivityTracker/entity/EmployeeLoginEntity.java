package com.example.EmployeeActivityTracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Data
@Table(name = "Employee_login")
public class EmployeeLoginEntity {

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;
    private String emailId;
    private String password;
    private String role;
    private String otp;

}
