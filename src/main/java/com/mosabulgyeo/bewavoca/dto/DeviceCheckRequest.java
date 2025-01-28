package com.mosabulgyeo.bewavoca.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DeviceCheckRequest 클래스
 * 클라이언트가 기기 정보를 서버로 전송하기 위한 요청 DTO 클래스.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCheckRequest {

	/**
	 * 사용자 기기 고유 식별자
	 * 비어 있을 수 없으며, 요청 데이터에 반드시 포함되어야 함.
	 */
	@NotBlank(message = "Device ID is required.")
	private String deviceId;
}