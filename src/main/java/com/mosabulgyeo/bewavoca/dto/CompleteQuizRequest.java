package com.mosabulgyeo.bewavoca.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 퀴즈 완료 요청 데이터 전송 객체
 */
@Getter
@Setter
public class CompleteQuizRequest {
	@NotBlank(message = "Device ID is required.")
	private String deviceId;

	@Min(value = 1, message = "Region must be greater than 0.")
	private int region;

	@Min(value = 1, message = "Stage must be greater than 0.")
	private int stage;

	private boolean isSuccess;
}