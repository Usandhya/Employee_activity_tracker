package com.example.EmployeeActivityTracker.repository;

import com.example.EmployeeActivityTracker.entity.ActivityTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityTrackerRepository extends JpaRepository<ActivityTracker,Integer> {
}
