
package com.example.EmployeeActivityTracker.controller;


import com.example.EmployeeActivityTracker.entity.Admin;
import com.example.EmployeeActivityTracker.entity.SendOtpEntity;
import com.example.EmployeeActivityTracker.repository.AdminRepository;
import com.example.EmployeeActivityTracker.repository.SendOtpRepository;
import com.example.EmployeeActivityTracker.request.AdminRequest;
import com.example.EmployeeActivityTracker.request.EmployeeLogin;
import com.example.EmployeeActivityTracker.request.SendOtp;
import com.example.EmployeeActivityTracker.response.ResultResponse;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;



import jakarta.mail.*;


@Controller
@Slf4j
public class AdminController {


    private Admin superAdmin;

    private final Map<String, LocalDateTime> otpExpiryMap = new HashMap<>();

    private final AdminRepository adminRepository;

    private final SendOtpRepository sendOtpRepository;

  @Autowired
    public AdminController(AdminRepository adminRepository,
                           SendOtpRepository sendOtpRepository) {
        this.adminRepository = adminRepository;
        this.sendOtpRepository=sendOtpRepository;

        String superAdminEmailId = "ujjaily.sandhya@gmail.com";
        String superAdminPassword = "UJJsan$9";

        superAdmin = adminRepository.findByEmailId(superAdminEmailId);

        if (superAdmin == null) {
            superAdmin = new Admin();
            superAdmin.setEmailId(superAdminEmailId);
            superAdmin.setPassword(superAdminPassword);
            adminRepository.save(superAdmin);
        }
    }

    @GetMapping("/adminform")
    public String adminLoginPage( Model model)
    {
        model.addAttribute("admin",new AdminRequest());
        return "adminlogin";
    }

    @GetMapping("/adminotp")
    public String generateOtp(Model model)
    {
        model.addAttribute("sendotp",new SendOtp());
        return "adminsendotp";
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


    @PostMapping("/adminlogin")
    public String login(@ModelAttribute AdminRequest adminRequest,Model model) {
        Admin adminentity = adminRepository.findByEmailIdAndPassword(adminRequest.getEmailId(),adminRequest.getPassword());

        if (adminentity==null) {;
            log.info("invalid otp for email: {}", adminRequest.getEmailId());
            model.addAttribute("msg","Invalid email or password");
            model.addAttribute("admin",new AdminRequest());
            return "adminlogin";

        }
        // Generate OTP and save it in the database
        String otp = generateOtp();
        Admin admin1 = adminRepository.findByEmailId(adminRequest.getEmailId()); // Check if an OTP entry already exists for the email ID
        if (admin1 == null) {
            admin1 = new Admin();
        }
        admin1.setEmailId(adminRequest.getEmailId());
        admin1.setOtp(otp);
        SendOtpEntity sendOtpEntity =sendOtpRepository.findByEmailId(adminRequest.emailId);
        if (sendOtpEntity == null) {
            sendOtpEntity = new SendOtpEntity();
        }
        sendOtpEntity.setEmailId(adminRequest.getEmailId());
        sendOtpEntity.setOtp(otp);

        adminRepository.save(admin1);
        sendOtpRepository.save(sendOtpEntity);

        // Send OTP to the email address
        sendEmail(adminRequest.emailId, otp);

        // Store the OTP and its expiry time
        otpExpiryMap.put(adminRequest.emailId, LocalDateTime.now().plusMinutes(5));
        log.info("OTP sent for email: {}", adminRequest.emailId);
        model.addAttribute("msg","OTP sent for email");
        model.addAttribute("sendotp",new SendOtp());
        return "adminsendotp";

    }

    @PostMapping("/otplogin")
    public String adminOtpLogin(@RequestParam String otp,@RequestParam String emailId,Model model)
    {
        SendOtpEntity sendOtpEntity=sendOtpRepository.findByEmailIdAndOtp(emailId,otp);
        if (sendOtpEntity==null) {
            log.info("invalid otp for email: {}", emailId);
            model.addAttribute("msg","Invalid emailId or otp");
            model.addAttribute("sendotp",new SendOtp());
            return "adminsendotp";
        }

        // Check if the OTP is expired
        LocalDateTime otpExpiryTime = otpExpiryMap.get(sendOtpEntity.getEmailId());
        if (otpExpiryTime == null || LocalDateTime.now().isAfter(otpExpiryTime)) {
            sendOtpRepository.delete(sendOtpEntity);
            otpExpiryMap.remove(sendOtpEntity.getEmailId());
            // Remove the OTP expiry time from the map
            log.info("otp is expired for email: {}", sendOtpEntity.getEmailId());
            model.addAttribute("msg","OTP expired");
            model.addAttribute("sendotp",new SendOtp());
            return "adminsendotp";
        }
        sendOtpEntity.setOtp(null);
        // If OTP is valid and not expired, clear the OTP and its expiry time
        Admin adminentity=adminRepository.findByEmailId(sendOtpEntity.getEmailId());
        adminentity.setOtp(null); // Set the OTP to null in the OTP entity
        adminRepository.save(adminentity); // Save the OTP entity with the updated value
        sendOtpRepository.save(sendOtpEntity);
        otpExpiryMap.remove(sendOtpEntity.getEmailId()); // Remove the OTP expiry time from the map

        // Update the last login time for the user in the login table
        //updateLastLoginTime(emailId);
        log.info("Login successful for email: {}", sendOtpEntity.getEmailId());
       // model.addAttribute("msg","Login successful");
        model.addAttribute("login",new EmployeeLogin());
        return "login";
    }

    }


