package com.example.EmployeeActivityTracker.request;

import lombok.Data;

import java.util.List;

@Data
public class ActivityTrackerRequest {

    private String timeSlot;
    private String activityName;
    private String comments;
}
