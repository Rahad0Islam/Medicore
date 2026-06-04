package com.appointment.appointment.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
		name = "doctor_daily_serials",
		uniqueConstraints = @UniqueConstraint(columnNames = {"doctorId", "serviceDate"})
)
@Getter
@Setter
public class DoctorDailySerial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String doctorId;

	@Column(nullable = false)
	private LocalDate serviceDate;

	@Column(nullable = false)
	private Integer lastSerial;
}
