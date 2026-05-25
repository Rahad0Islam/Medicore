package com.userservice.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userservice.user_service.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByEmail(String email);
    Optional<Patient> findByEmail(String email);
}
