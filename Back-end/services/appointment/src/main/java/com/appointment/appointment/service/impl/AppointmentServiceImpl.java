package com.appointment.appointment.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appointment.appointment.dto.AppointmentResponse;
import com.appointment.appointment.dto.BookAppointmentRequest;
import com.appointment.appointment.dto.QueueItemResponse;
import com.appointment.appointment.entity.Appointment;
import com.appointment.appointment.entity.AppointmentStatus;
import com.appointment.appointment.exception.ConflictException;
import com.appointment.appointment.exception.ResourceNotFoundException;
import com.appointment.appointment.repository.AppointmentRepository;
import com.appointment.appointment.service.AppointmentService;
import com.appointment.appointment.service.SerialNumberGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final SerialNumberGenerator serialNumberGenerator;

	@Override
	@Transactional
	public AppointmentResponse bookAppointment(BookAppointmentRequest request) {
		boolean slotTaken = appointmentRepository.existsByDoctorIdAndAppointmentDateAndTimeSlot(
				request.getDoctorId(),
				request.getAppointmentDate(),
				request.getTimeSlot()
		);
		if (slotTaken) {
			throw new ConflictException("Time slot already booked for this doctor.");
		}

		int nextSerial = serialNumberGenerator.nextSerial(request.getDoctorId(), request.getAppointmentDate());

		Appointment appointment = new Appointment();
		appointment.setPatientId(request.getPatientId());
		appointment.setDoctorId(request.getDoctorId());
		appointment.setAppointmentDate(request.getAppointmentDate());
		appointment.setTimeSlot(request.getTimeSlot());
		appointment.setSerialNumber(nextSerial);
		appointment.setStatus(AppointmentStatus.BOOKED);
		appointment.setReason(request.getReason());

		Appointment saved = appointmentRepository.save(appointment);
		return AppointmentResponse.from(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public AppointmentResponse getAppointment(Long id) {
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment not found."));
		return AppointmentResponse.from(appointment);
	}

	@Override
	@Transactional
	public AppointmentResponse cancelAppointment(Long id) {
		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment not found."));
		appointment.setStatus(AppointmentStatus.CANCELLED);
		return AppointmentResponse.from(appointmentRepository.save(appointment));
	}

	@Override
	@Transactional(readOnly = true)
	public List<QueueItemResponse> getQueue(
			String doctorId,
			LocalDate appointmentDate,
			AppointmentStatus status
	) {
		AppointmentStatus effectiveStatus = status == null ? AppointmentStatus.BOOKED : status;
		return appointmentRepository
				.findByDoctorIdAndAppointmentDateAndStatusOrderBySerialNumberAsc(
						doctorId,
						appointmentDate,
						effectiveStatus
				)
				.stream()
				.map(QueueItemResponse::from)
				.toList();
	}
}
