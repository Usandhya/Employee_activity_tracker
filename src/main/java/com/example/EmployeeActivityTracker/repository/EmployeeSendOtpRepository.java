package com.example.EmployeeActivityTracker.repository;

import com.example.EmployeeActivityTracker.entity.EmployeeLoginEntity;
import com.example.EmployeeActivityTracker.entity.EmployeeSendOtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSendOtpRepository extends JpaRepository<EmployeeSendOtpEntity,String> {

    EmployeeSendOtpEntity findByEmailId(String emailId);

    EmployeeSendOtpEntity findByEmailIdAndOtp(String emailId,String otp);
}
