package com.userservice.user_service.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DoctorResponse {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String specialization;
    private String department;
    private String licenseNumber;
    private boolean enabled;
    private Instant createdAt;
}
