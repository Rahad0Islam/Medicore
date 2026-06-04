package com.appointment.appointment.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.appointment.dto.AppointmentResponse;
import com.appointment.appointment.dto.BookAppointmentRequest;
import com.appointment.appointment.dto.QueueItemResponse;
import com.appointment.appointment.entity.AppointmentStatus;
import com.appointment.appointment.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

	private final AppointmentService appointmentService;

	@PostMapping("/book")
	public ResponseEntity<AppointmentResponse> bookAppointment(
			@Valid @RequestBody BookAppointmentRequest request
	) {
		AppointmentResponse response = appointmentService.bookAppointment(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id:\\d+}")
	public AppointmentResponse getAppointment(@PathVariable Long id) {
		return appointmentService.getAppointment(id);
	}

	@PutMapping("/{id:\\d+}/cancel")
	public AppointmentResponse cancelAppointment(@PathVariable Long id) {
		return appointmentService.cancelAppointment(id);
	}

	@GetMapping("/queue")
	public List<QueueItemResponse> getQueue(
			@RequestParam String doctorId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(required = false) AppointmentStatus status
	) {
		return appointmentService.getQueue(doctorId, date, status);
	}
}
