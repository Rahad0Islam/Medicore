package com.userservice.user_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pharmacists")
@Getter
@Setter
@NoArgsConstructor
public class Pharmacist extends BaseUser {

	@Column(length = 120)
	private String registrationNumber;

	@Column(length = 255)
	private String pharmacyName;
}