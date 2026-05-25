package com.userservice.user_service.service;

import java.util.List;

import com.userservice.user_service.dto.PatientRequest;
import com.userservice.user_service.dto.PatientResponse;

public interface PatientService {
    PatientResponse create(String email, String name, PatientRequest request);
    PatientResponse getById(Long id);
    List<PatientResponse> getAll();
    PatientResponse update(Long id, String email, String name, PatientRequest request);
    void delete(Long id);
}
