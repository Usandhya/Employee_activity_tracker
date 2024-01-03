package com.example.EmployeeActivityTracker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class EmployeeRegistrationService {

    public List<String> getSkillSet()
    {
        return Arrays.asList("JAVA","DOTNET","SOFTWARE TESTING","PYTHON","WEB DEVELOPER");
    }

    public List<String> getActivityNames()
    {
        return Arrays.asList("TRAINING","PROJECT","MEETING","SEMINAR","TEAM COLLABORATION","LUNCH(45MIN)","BREAK1(15MIN)","BREAK2(15min)");
    }
}
