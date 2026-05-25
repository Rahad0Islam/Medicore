package com.userservice.user_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.user_service.dto.DoctorRequest;
import com.userservice.user_service.dto.DoctorResponse;
import com.userservice.user_service.security.UserPrincipal;
import com.userservice.user_service.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> create(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.create(principal.getEmail(), principal.getName(), request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAll(@RequestParam(value = "query", required = false) String query) {
        if (query != null && !query.isBlank()) {
            return ResponseEntity.ok(doctorService.search(query));
        }
        return ResponseEntity.ok(doctorService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<DoctorResponse>> search(@RequestParam("query") String query) {
        return ResponseEntity.ok(doctorService.search(query));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> update(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.ok(doctorService.update(id, principal.getEmail(), principal.getName(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
