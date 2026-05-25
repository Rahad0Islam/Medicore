package com.userservice.user_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 50)
    private String phone;

    @Size(max = 20)
    private String gender;

    private LocalDate dateOfBirth;

    @Size(max = 500)
    private String address;

    private Boolean enabled;
}
