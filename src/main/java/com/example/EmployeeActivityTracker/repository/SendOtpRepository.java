package com.example.EmployeeActivityTracker.repository;

import com.example.EmployeeActivityTracker.entity.EmployeeLoginEntity;
import com.example.EmployeeActivityTracker.entity.SendOtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendOtpRepository extends JpaRepository<SendOtpEntity,String> {

    SendOtpEntity findByEmailId(String emailId);

    SendOtpEntity findByEmailIdAndOtp(String emailId,String otp);
}
