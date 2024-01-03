package com.example.EmployeeActivityTracker.controller;

import com.example.EmployeeActivityTracker.entity.EmployeeEntity;
import com.example.EmployeeActivityTracker.entity.EmployeeLoginEntity;
import com.example.EmployeeActivityTracker.entity.SkillSet;
import com.example.EmployeeActivityTracker.mapper.EmployeeActivityTrackerMapper;
import com.example.EmployeeActivityTracker.repository.EmployeeRepository;
import com.example.EmployeeActivityTracker.repository.LoginRpository;
import com.example.EmployeeActivityTracker.repository.SkillSetRepository;
import com.example.EmployeeActivityTracker.request.EmployeeRequest;
import com.example.EmployeeActivityTracker.service.EmployeeRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class EmployeeRegistrationController {

    @Autowired
    private EmployeeRegistrationService customerRegistrationservice;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LoginRpository loginRepository;


    @Autowired
    private SkillSetRepository skillSetRepository;

    @Autowired
    private EmployeeActivityTrackerMapper employeeActivityTrackerMapper;

    @GetMapping("/")
    public String loadIndexPage(Model model) {
        model.addAttribute("employee", new EmployeeRequest());
        model.addAttribute("skillset", customerRegistrationservice.getSkillSet());
        return "index";
    }

    @PostMapping("/register")
    public String empRegistration(@ModelAttribute("employee") EmployeeRequest employeeRequest, Model model) {

        EmployeeEntity existingUser = employeeRepository.findByEmailId(employeeRequest.getEmailId());
        if (existingUser != null) {
            // Email already exists in the database
            log.info("Email exists in the db: {}", employeeRequest.getEmailId());
            model.addAttribute("emp","error occurred while while registering or emailId alraedy exist in db");
            model.addAttribute("employee", new EmployeeRequest());
            model.addAttribute("skillset", customerRegistrationservice.getSkillSet());
            return "index";

        } else {
            EmployeeEntity employeeEntity = employeeActivityTrackerMapper.requestToEntity(employeeRequest);
            String highestDomainHostMappingId = employeeRepository.findHighestId();
            int numericPart = 1;

            if (highestDomainHostMappingId != null) {
                numericPart = Integer.parseInt(highestDomainHostMappingId.substring(3)) + 1;
            }
            String idFormat = "EMP%03d"; // The %03d means a 3-digit zero-padded number
            String empId = String.format(idFormat, numericPart);
            employeeEntity.setEmpID(empId);
            employeeEntity.setSkillSet(employeeRequest.getSkillSet());

            EmployeeLoginEntity employeeLoginEntity=new EmployeeLoginEntity();
            employeeLoginEntity.setEmailId(employeeRequest.getEmailId());
            employeeLoginEntity.setPassword(employeeRequest.getPassword());
            employeeLoginEntity.setRole(employeeRequest.getRole());

            SkillSet skillSet=new SkillSet();
            skillSet.setEmailId(employeeRequest.getEmailId());
            skillSet.setSkillSet(employeeRequest.getSkillSet());
            skillSet.setFirstName(employeeRequest.getFirstName());
            skillSet.setLastName(employeeRequest.getLastName());

            employeeRepository.save(employeeEntity);
            loginRepository.save(employeeLoginEntity);
            skillSetRepository.save(skillSet);
        }
        model.addAttribute("emp","employee registered");
        model.addAttribute("employee", new EmployeeRequest());
        model.addAttribute("skillset", customerRegistrationservice.getSkillSet());

        return "index";
            }
}
