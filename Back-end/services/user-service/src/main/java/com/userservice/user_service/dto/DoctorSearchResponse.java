package com.userservice.user_service.dto;

public record DoctorSearchResponse(
		Long id,
		String name,
		String specialization,
		String department,
		String licenseNumber,
		Double rating) {
}