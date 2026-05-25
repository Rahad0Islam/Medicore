package com.userservice.user_service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRequest {

    @Size(max = 50)
    private String phone;

    @Size(max = 20)
    private String gender;

    private LocalDate dateOfBirth;

    @Size(max = 500)
    private String address;

    private Boolean enabled;
}
