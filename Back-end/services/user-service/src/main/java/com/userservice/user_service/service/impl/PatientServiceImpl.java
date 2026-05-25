package com.userservice.user_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.userservice.user_service.dto.PatientRequest;
import com.userservice.user_service.dto.PatientResponse;
import com.userservice.user_service.entity.Patient;
import com.userservice.user_service.exception.ResourceNotFoundException;
import com.userservice.user_service.repository.PatientRepository;
import com.userservice.user_service.service.PatientService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientResponse create(PatientRequest request) {
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        Patient patient = new Patient();
        applyRequest(patient, request);
        Patient saved = patientRepository.save(patient);
        return toResponse(saved);
    }

    @Override
    public PatientResponse getById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        return toResponse(patient);
    }

    @Override
    public List<PatientResponse> getAll() {
        return patientRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PatientResponse update(Long id, PatientRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        if (!patient.getEmail().equalsIgnoreCase(request.getEmail())
                && patientRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        applyRequest(patient, request);
        return toResponse(patientRepository.save(patient));
    }

    @Override
    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found");
        }
        patientRepository.deleteById(id);
    }

    private void applyRequest(Patient patient, PatientRequest request) {
        patient.setEmail(request.getEmail());
        patient.setName(request.getName());
        patient.setPhone(request.getPhone());
        patient.setGender(request.getGender());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setAddress(request.getAddress());
        if (request.getEnabled() != null) {
            patient.setEnabled(request.getEnabled());
        }
    }

    private PatientResponse toResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getEmail(),
                patient.getName(),
                patient.getPhone(),
                patient.getGender(),
                patient.getDateOfBirth(),
                patient.getAddress(),
                patient.isEnabled(),
                patient.getCreatedAt());
    }
}
