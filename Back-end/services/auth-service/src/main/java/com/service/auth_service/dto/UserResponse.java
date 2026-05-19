package com.service.auth_service.dto;

import com.service.auth_service.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private Role role;
    private boolean enabled;
}
