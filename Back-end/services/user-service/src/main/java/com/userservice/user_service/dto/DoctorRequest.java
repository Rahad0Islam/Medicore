package com.userservice.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 50)
    private String phone;

    @NotBlank
    @Size(max = 120)
    private String specialization;

    @Size(max = 120)
    private String department;

    @Size(max = 120)
    private String licenseNumber;

    private Boolean enabled;
}
