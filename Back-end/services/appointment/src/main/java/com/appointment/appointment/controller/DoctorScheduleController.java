package com.appointment.appointment.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.appointment.dto.QueueStatusDTO;
import com.appointment.appointment.entity.AppointmentStatus;
import com.appointment.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/doctor-schedules")
@RequiredArgsConstructor
public class DoctorScheduleController {

	private final AppointmentService appointmentService;

	@GetMapping("/{doctorId}")
	public QueueStatusDTO getSchedule(
			@PathVariable String doctorId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(required = false) AppointmentStatus status
	) {
		var items = appointmentService.getQueue(doctorId, date, status);
		return QueueStatusDTO.builder()
				.doctorId(doctorId)
				.date(date)
				.totalAppointments(items.size())
				.nextSerial(items.size() + 1)
				.queueItems(items)
				.build();
	}
}