package com.userservice.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userservice.user_service.entity.Pharmacist;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
	boolean existsByEmail(String email);
	Optional<Pharmacist> findByEmail(String email);
}