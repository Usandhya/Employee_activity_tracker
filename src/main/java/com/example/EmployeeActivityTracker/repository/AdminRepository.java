package com.example.EmployeeActivityTracker.repository;


import com.example.EmployeeActivityTracker.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
        Admin findByEmailId(String emailId);

        Admin findByEmailIdAndPassword(String emailId,String password);

}
