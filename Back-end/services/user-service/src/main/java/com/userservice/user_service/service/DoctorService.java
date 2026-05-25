package com.userservice.user_service.service;

import java.util.List;

import com.userservice.user_service.dto.DoctorRequest;
import com.userservice.user_service.dto.DoctorResponse;

public interface DoctorService {
    DoctorResponse create(DoctorRequest request);
    DoctorResponse getById(Long id);
    List<DoctorResponse> getAll();
    List<DoctorResponse> search(String query);
    DoctorResponse update(Long id, DoctorRequest request);
    void delete(Long id);
}
