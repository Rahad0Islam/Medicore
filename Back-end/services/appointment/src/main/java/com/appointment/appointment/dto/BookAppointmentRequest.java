package com.appointment.appointment.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookAppointmentRequest {

	@NotBlank
	private String patientId;

	@NotBlank
	private String doctorId;

	@NotNull
	private LocalDate appointmentDate;

	@NotBlank
	private String timeSlot;

	private String reason;
}
