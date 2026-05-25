package com.userservice.user_service.dto;

import java.time.Instant;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatientResponse {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private boolean enabled;
    private Instant createdAt;
}
