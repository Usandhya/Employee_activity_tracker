package com.example.EmployeeActivityTracker.repository;

import com.example.EmployeeActivityTracker.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,String> {

    @Query("SELECT MAX(m.empID) FROM EmployeeEntity m")
    String findHighestId();

    EmployeeEntity findByEmailId(String emailId);

    @Query("select u from EmployeeEntity u where u.emailId= :emailId")
  public EmployeeEntity getEmployeeByEmployeeName(@Param("emailId") String emailId);

}
