package com.appointment.appointment.service;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.appointment.appointment.entity.DoctorDailySerial;
import com.appointment.appointment.repository.DoctorDailySerialRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SerialNumberGenerator {

	private final DoctorDailySerialRepository doctorDailySerialRepository;

	@Transactional
	public int nextSerial(String doctorId, LocalDate serviceDate) {
		DoctorDailySerial serialTracker = doctorDailySerialRepository
				.findForUpdate(doctorId, serviceDate)
				.orElseGet(() -> {
					DoctorDailySerial fresh = new DoctorDailySerial();
					fresh.setDoctorId(doctorId);
					fresh.setServiceDate(serviceDate);
					fresh.setLastSerial(0);
					return fresh;
				});

		serialTracker.setLastSerial(serialTracker.getLastSerial() + 1);
		doctorDailySerialRepository.save(serialTracker);
		return serialTracker.getLastSerial();
	}
}