package com.appointment.appointment.dto;

import java.time.Instant;
import java.time.LocalDate;

import com.appointment.appointment.entity.Appointment;
import com.appointment.appointment.entity.AppointmentStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppointmentResponse {

	private Long id;
	private String patientId;
	private String doctorId;
	private LocalDate appointmentDate;
	private String timeSlot;
	private Integer serialNumber;
	private AppointmentStatus status;
	private String reason;
	private Instant createdAt;
	private Instant updatedAt;

	public static AppointmentResponse from(Appointment appointment) {
		return AppointmentResponse.builder()
				.id(appointment.getId())
				.patientId(appointment.getPatientId())
				.doctorId(appointment.getDoctorId())
				.appointmentDate(appointment.getAppointmentDate())
				.timeSlot(appointment.getTimeSlot())
				.serialNumber(appointment.getSerialNumber())
				.status(appointment.getStatus())
				.reason(appointment.getReason())
				.createdAt(appointment.getCreatedAt())
				.updatedAt(appointment.getUpdatedAt())
				.build();
	}
}
