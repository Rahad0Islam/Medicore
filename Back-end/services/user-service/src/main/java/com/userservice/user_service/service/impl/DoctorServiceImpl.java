package com.userservice.user_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.userservice.user_service.dto.DoctorRequest;
import com.userservice.user_service.dto.DoctorResponse;
import com.userservice.user_service.entity.Doctor;
import com.userservice.user_service.exception.ResourceNotFoundException;
import com.userservice.user_service.repository.DoctorRepository;
import com.userservice.user_service.service.DoctorService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public DoctorResponse create(DoctorRequest request) {
        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        Doctor doctor = new Doctor();
        applyRequest(doctor, request);
        Doctor saved = doctorRepository.save(doctor);
        return toResponse(saved);
    }

    @Override
    public DoctorResponse getById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        return toResponse(doctor);
    }

    @Override
    public List<DoctorResponse> getAll() {
        return doctorRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<DoctorResponse> search(String query) {
        String safeQuery = query == null ? "" : query.trim();
        if (safeQuery.isEmpty()) {
            return getAll();
        }
        return doctorRepository.findByNameContainingIgnoreCaseOrSpecializationContainingIgnoreCase(safeQuery, safeQuery)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public DoctorResponse update(Long id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        if (!doctor.getEmail().equalsIgnoreCase(request.getEmail())
                && doctorRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        applyRequest(doctor, request);
        return toResponse(doctorRepository.save(doctor));
    }

    @Override
    public void delete(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found");
        }
        doctorRepository.deleteById(id);
    }

    private void applyRequest(Doctor doctor, DoctorRequest request) {
        doctor.setEmail(request.getEmail());
        doctor.setName(request.getName());
        doctor.setPhone(request.getPhone());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setDepartment(request.getDepartment());
        doctor.setLicenseNumber(request.getLicenseNumber());
        if (request.getEnabled() != null) {
            doctor.setEnabled(request.getEnabled());
        }
    }

    private DoctorResponse toResponse(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getEmail(),
                doctor.getName(),
                doctor.getPhone(),
                doctor.getSpecialization(),
                doctor.getDepartment(),
                doctor.getLicenseNumber(),
                doctor.isEnabled(),
                doctor.getCreatedAt());
    }
}
