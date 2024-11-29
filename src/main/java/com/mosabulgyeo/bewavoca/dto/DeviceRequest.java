package com.mosabulgyeo.bewavoca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DeviceRequest 클래스
 * 클라이언트가 기기 식별 정보를 서버로 전송하기 위한 요청 DTO 클래스.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRequest {

	/**
	 * 사용자 기기 고유 식별자.
	 * 비어 있을 수 없으며, 요청 데이터에 반드시 포함되어야 함.
	 * 예: "ABC12345"
	 */
	@NotBlank(message = "Device ID is required.")
	@Size(min = 5, max = 50, message = "Device ID must be between 5 and 50 characters.")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Device ID must contain only letters and numbers.")
	private String deviceId;
}