package com.example.EmployeeActivityTracker.mapper;
import com.example.EmployeeActivityTracker.entity.ActivityTracker;
import com.example.EmployeeActivityTracker.entity.EmployeeEntity;
import com.example.EmployeeActivityTracker.entity.EmployeeLoginEntity;
import com.example.EmployeeActivityTracker.request.ActivityTrackerRequest;
import com.example.EmployeeActivityTracker.request.EmployeeLogin;
import com.example.EmployeeActivityTracker.request.EmployeeRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface EmployeeActivityTrackerMapper {

    EmployeeEntity requestToEntity(EmployeeRequest request);

    ActivityTracker requestToEntity(ActivityTrackerRequest request);


}
