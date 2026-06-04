package com.appointment.appointment.service;

import java.time.LocalDate;
import java.util.List;

import com.appointment.appointment.dto.AppointmentResponse;
import com.appointment.appointment.dto.BookAppointmentRequest;
import com.appointment.appointment.dto.QueueItemResponse;
import com.appointment.appointment.entity.AppointmentStatus;

public interface AppointmentService {

	AppointmentResponse bookAppointment(BookAppointmentRequest request);

	AppointmentResponse getAppointment(Long id);

	AppointmentResponse cancelAppointment(Long id);

	List<QueueItemResponse> getQueue(String doctorId, LocalDate appointmentDate, AppointmentStatus status);
}
