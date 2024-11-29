package com.mosabulgyeo.bewavoca.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 퀴즈 완료 요청 데이터 전송 객체
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteQuizRequest {
	@NotNull(message = "User ID is required.")
	private Long userId;

	@NotNull(message = "Region ID is required.")
	private Long regionId;

	@NotNull(message = "Stage type is required.")
	private String stageType;

	@NotNull(message = "Pass/fail result is required.")
	private Boolean isPassed;
}