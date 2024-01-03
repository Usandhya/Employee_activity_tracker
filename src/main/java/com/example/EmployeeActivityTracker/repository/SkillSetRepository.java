package com.example.EmployeeActivityTracker.repository;

import com.example.EmployeeActivityTracker.entity.SkillSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillSetRepository extends JpaRepository<SkillSet,String>
{
 @Query(value = "SELECT skillset FROM SkillSet\n" +
         " where emailId=emailId;",nativeQuery = true)
List<String> getSkillSetByEmailId(String emailId);

}
