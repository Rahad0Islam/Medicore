package com.appointment.appointment.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueueStatusDTO {

	private String doctorId;
	private LocalDate date;
	private Integer totalAppointments;
	private Integer nextSerial;
	private List<QueueItemResponse> queueItems;
}