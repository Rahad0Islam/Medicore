package com.userservice.user_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.user_service.entity.Pharmacist;
import com.userservice.user_service.repository.PharmacistRepository;

@RestController
@RequestMapping("/api/users/pharmacists")
public class PharmacistController {

	private final PharmacistRepository pharmacistRepository;

	public PharmacistController(PharmacistRepository pharmacistRepository) {
		this.pharmacistRepository = pharmacistRepository;
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('PHARMACIST','ADMIN')")
	public List<Pharmacist> getAll() {
		return pharmacistRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('PHARMACIST','ADMIN')")
	public ResponseEntity<Pharmacist> create(@RequestBody Pharmacist pharmacist) {
		return ResponseEntity.status(HttpStatus.CREATED).body(pharmacistRepository.save(pharmacist));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		pharmacistRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}