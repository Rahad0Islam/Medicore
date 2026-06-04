package com.appointment.appointment.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.appointment.appointment.entity.DoctorDailySerial;

import jakarta.persistence.LockModeType;

public interface DoctorDailySerialRepository extends JpaRepository<DoctorDailySerial, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select d from DoctorDailySerial d where d.doctorId = :doctorId and d.serviceDate = :serviceDate")
	Optional<DoctorDailySerial> findForUpdate(
			@Param("doctorId") String doctorId,
			@Param("serviceDate") LocalDate serviceDate
	);
}
