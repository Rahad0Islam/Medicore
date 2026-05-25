package com.userservice.user_service.service;

import java.util.List;

import com.userservice.user_service.dto.PatientRequest;
import com.userservice.user_service.dto.PatientResponse;

public interface PatientService {
    PatientResponse create(PatientRequest request);
    PatientResponse getById(Long id);
    List<PatientResponse> getAll();
    PatientResponse update(Long id, PatientRequest request);
    void delete(Long id);
}
