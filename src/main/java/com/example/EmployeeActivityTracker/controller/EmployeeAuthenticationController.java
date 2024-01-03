package com.example.EmployeeActivityTracker.controller;



import com.example.EmployeeActivityTracker.entity.*;
import com.example.EmployeeActivityTracker.repository.EmployeeRepository;
import com.example.EmployeeActivityTracker.repository.EmployeeSendOtpRepository;
import com.example.EmployeeActivityTracker.repository.SendOtpRepository;
import com.example.EmployeeActivityTracker.request.EmployeeRequest;
import com.example.EmployeeActivityTracker.request.EmployeeSendOtp;
import com.example.EmployeeActivityTracker.request.SendOtp;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import com.example.EmployeeActivityTracker.repository.LoginRpository;
import com.example.EmployeeActivityTracker.request.EmployeeLogin;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
public class EmployeeAuthenticationController {

    @Autowired
    private EmployeeRepository employeeRepository;
    private final Map<String, LocalDateTime> otpExpiryMap = new HashMap<>();

    @Autowired

    private EmployeeSendOtpRepository employeeSendOtpRepository;

    @Autowired
    private LoginRpository loginRpository;


    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("login", new EmployeeLogin());
        return "login";
    }

   /*@GetMapping("/parent")
    public String parent()
    {
        return "homepage";
    }*/

    @GetMapping("/otp")
    public String generateOtp(Model model) {
        model.addAttribute("sendotp", new EmployeeSendOtp());
        return "sendotp";
    }


    private String generateOtp() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void sendEmail(String recipientEmailId, String otp) {
        // SMTP server properties (You can replace this with your actual email sending logic)
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Sender's credentials
        final String senderEmailId = "pbtjava@gmail.com"; // Replace with your email
        final String senderPassword = "jcnsrxqjifkrvqxl"; // Replace with your password

        // Create a session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmailId, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);
            // Set From: header
            message.setFrom(new InternetAddress(senderEmailId));
            // Set To: header
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmailId));
            // Set Subject: header
            message.setSubject("OTP Verification");
            // Set Content: text/plain
            message.setText("Your OTP: " + otp);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/emplogin")
    public String login(@RequestParam String emailId, @RequestParam String password, Model model) {
        // Check if OTP exists in the repository
        EmployeeLoginEntity emp = loginRpository.findByEmailIdAndPassword(emailId, password);
        if (emp == null) {
            log.info("Invalid emailId: {}", emailId);
            model.addAttribute("msg", "Invalid emailId or password");
            model.addAttribute("login", new EmployeeLogin());
            return "login";
        }

        // Generate OTP and save it in the database
        String otp = generateOtp();
        EmployeeLoginEntity emp1 = loginRpository.findByEmailId(emailId); // Check if an OTP entry already exists for the email ID
        if (emp1 == null) {
            emp1 = new EmployeeLoginEntity();
        }
        emp1.setEmailId(emailId);
        emp1.setOtp(otp);
        EmployeeSendOtpEntity sendOtpEntity = employeeSendOtpRepository.findByEmailId(emailId);
        if (sendOtpEntity == null) {
            sendOtpEntity = new EmployeeSendOtpEntity();
        }
        sendOtpEntity.setEmailId(emailId);
        sendOtpEntity.setOtp(otp);

        loginRpository.save(emp1);
        employeeSendOtpRepository.save(sendOtpEntity);

        // Send OTP to the email address
        sendEmail(emailId, otp);

        // Store the OTP and its expiry time
        otpExpiryMap.put(emailId, LocalDateTime.now().plusMinutes(5));
        log.info("OTP sent for email: {}", emailId);
        model.addAttribute("msg", "OTP sent for email");
        model.addAttribute("sendotp", new SendOtp());
        return "sendotp";
    }


    @PostMapping("/empotplogin")
    public String empOtpLogin(@RequestParam String otp, @RequestParam String emailId, Model model) {
        EmployeeSendOtpEntity sendOtpEntity = employeeSendOtpRepository.findByEmailIdAndOtp(emailId, otp);
        if (sendOtpEntity == null) {
            log.info("invalid otp for email: {}", emailId);
            model.addAttribute("msg", "Invalid emailId or otp");
            model.addAttribute("sendotp", new EmployeeSendOtp());
            return "sendotp";
        }

        // Check if the OTP is expired
        LocalDateTime otpExpiryTime = otpExpiryMap.get(sendOtpEntity.getEmailId());
        if (otpExpiryTime == null || LocalDateTime.now().isAfter(otpExpiryTime)) {
            employeeSendOtpRepository.delete(sendOtpEntity);
            otpExpiryMap.remove(sendOtpEntity.getEmailId());
            // Remove the OTP expiry time from the map
            log.info("otp is expired for email: {}", sendOtpEntity.getEmailId());
            model.addAttribute("msg", "OTP expired");
            model.addAttribute("sendotp", new EmployeeSendOtp());
            return "sendotp";
        }
        sendOtpEntity.setOtp(null);
        // If OTP is valid and not expired, clear the OTP and its expiry time
        EmployeeLoginEntity loginentity = loginRpository.findByEmailId(sendOtpEntity.getEmailId());
            loginentity.setOtp(null); // Set the OTP to null in the OTP entity
            loginRpository.save(loginentity); // Save the OTP entity with the updated value
            employeeSendOtpRepository.save(sendOtpEntity);
            otpExpiryMap.remove(sendOtpEntity.getEmailId()); // Remove the OTP expiry time from the map

            // Update the last login time for the user in the login table
            //updateLastLoginTime(emailId);
            log.info("Login successful for email: {}", sendOtpEntity.getEmailId());
            //model.addAttribute("msg","Login successful");
            model.addAttribute("sendotp", new EmployeeSendOtp());
        return "homepage";

    }

    @GetMapping("/skillset")
    public String getSkillSet(@ModelAttribute EmployeeRequest employeeRequest, Model model)
    {
        EmployeeEntity employeeEntity=employeeRepository.findByEmailId(employeeRequest.getEmailId());
        if(employeeEntity!=null)
        {
            model.addAttribute("skillset",employeeEntity.getSkillSet());
        }
        return "homepage";
    }
}
