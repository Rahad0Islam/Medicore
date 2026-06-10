package com.userservice.user_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userservice.user_service.entity.Doctor;

public interface DoctorSpecializationRepository extends JpaRepository<Doctor, Long> {
	List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);
}