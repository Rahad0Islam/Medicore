package com.userservice.user_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userservice.user_service.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByEmail(String email);
    Optional<Doctor> findByEmail(String email);
    List<Doctor> findByNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(String name, String specialization);
}
