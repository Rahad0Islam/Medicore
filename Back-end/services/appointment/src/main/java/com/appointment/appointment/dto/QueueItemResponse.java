package com.appointment.appointment.dto;

import java.time.LocalDate;

import com.appointment.appointment.entity.Appointment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueueItemResponse {

	private Long appointmentId;
	private String patientId;
	private String doctorId;
	private LocalDate appointmentDate;
	private String timeSlot;
	private Integer serialNumber;
	private String status;

	public static QueueItemResponse from(Appointment appointment) {
		return QueueItemResponse.builder()
				.appointmentId(appointment.getId())
				.patientId(appointment.getPatientId())
				.doctorId(appointment.getDoctorId())
				.appointmentDate(appointment.getAppointmentDate())
				.timeSlot(appointment.getTimeSlot())
				.serialNumber(appointment.getSerialNumber())
				.status(appointment.getStatus().name())
				.build();
	}
}
