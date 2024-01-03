package com.example.EmployeeActivityTracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "SKILL_SET")
public class SkillSet {

    @Id
    private String emailId;

    private String skillSet;

    private String firstName;

    private String lastName;
}
