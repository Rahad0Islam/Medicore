package com.userservice.user_service.dto;

import java.time.LocalDate;

public record PatientProfileDTO(
		Long id,
		String email,
		String name,
		String phone,
		String gender,
		LocalDate dateOfBirth,
		String address,
		Boolean enabled) {
}