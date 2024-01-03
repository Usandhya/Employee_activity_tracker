package com.example.EmployeeActivityTracker.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name="adminTable")
public class Admin {
        @Id
        private String emailId;
        private String password;
        private String otp;
}