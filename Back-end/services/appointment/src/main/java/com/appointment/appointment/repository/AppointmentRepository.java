package com.appointment.appointment.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment.appointment.entity.Appointment;
import com.appointment.appointment.entity.AppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	boolean existsByDoctorIdAndAppointmentDateAndTimeSlot(
			String doctorId,
			LocalDate appointmentDate,
			String timeSlot
	);

	List<Appointment> findByDoctorIdAndAppointmentDateAndStatusOrderBySerialNumberAsc(
			String doctorId,
			LocalDate appointmentDate,
			AppointmentStatus status
	);

	Optional<Appointment> findByIdAndDoctorId(Long id, String doctorId);
}
