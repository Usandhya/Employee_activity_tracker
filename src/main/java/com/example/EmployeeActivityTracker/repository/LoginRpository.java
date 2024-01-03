package com.example.EmployeeActivityTracker.repository;

import com.example.EmployeeActivityTracker.entity.EmployeeEntity;
import com.example.EmployeeActivityTracker.entity.EmployeeLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRpository extends JpaRepository<EmployeeLoginEntity,Integer> {

    EmployeeLoginEntity findByEmailId(String emailId);

    EmployeeLoginEntity findByEmailIdAndPassword(String emailId,String password);

}
