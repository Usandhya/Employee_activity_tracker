package com.example.EmployeeActivityTracker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "EMPLOYEE_TABLE")
public class EmployeeEntity {

    @Id
    @Column(name = "empid")
    private String empID;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "skill_set")
    private String skillSet;
    @Column(name = "technology")
    private String technology;
    @Column(name = "reporting_manager")
    private String reportingManager;
    @Column(name = "doj")
    private String doj;
    @Column(name = "role")
    private String role;
    @Column(name = "email_id")
    private String emailId;
    @Column(name = "password")
    private String password;
}
