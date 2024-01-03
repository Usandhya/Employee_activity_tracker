package com.example.EmployeeActivityTracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class ActivityTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeTrackerId;
    private String emailId;
    private LocalTime time;
    private String activityName;
    private String comments;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
