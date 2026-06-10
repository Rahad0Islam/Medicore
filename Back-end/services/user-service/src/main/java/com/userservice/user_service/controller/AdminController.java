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

import com.userservice.user_service.entity.Admin;
import com.userservice.user_service.repository.AdminRepository;

@RestController
@RequestMapping("/api/users/admins")
public class AdminController {

	private final AdminRepository adminRepository;

	public AdminController(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<Admin> getAll() {
		return adminRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Admin> create(@RequestBody Admin admin) {
		return ResponseEntity.status(HttpStatus.CREATED).body(adminRepository.save(admin));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		adminRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}