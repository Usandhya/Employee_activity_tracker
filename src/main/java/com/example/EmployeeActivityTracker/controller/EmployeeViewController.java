package com.example.EmployeeActivityTracker.controller;


import com.example.EmployeeActivityTracker.request.ActivityTrackerRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeViewController {

    @GetMapping("/activity tracker")
    public String loadPage(Model model)
    {
        model.addAttribute("activity tracker",new ActivityTrackerRequest());
        return "addactivitytracker";
    }

}