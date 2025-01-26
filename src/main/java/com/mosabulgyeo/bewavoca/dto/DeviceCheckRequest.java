package com.mosabulgyeo.bewavoca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DeviceCheckRequest 클래스
 * 클라이언트가 기기 및 사용자 정보를 서버로 전송하기 위한 요청 DTO 클래스.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCheckRequest {

	/**
	 * 사용자 기기 고유 식별자.
	 * 비어 있을 수 없으며, 요청 데이터에 반드시 포함되어야 함.
	 * 예: "ABC12345"
	 */
	@NotBlank(message = "Device ID is required.")
	@Size(min = 5, max = 50, message = "Device ID must be between 5 and 50 characters.")
	@Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "Device ID must contain only letters, numbers, and dashes.")
	private String deviceId;

	/**
	 * 사용자 ID
	 */
	@NotBlank(message = "User ID is required.")
	private String userid;

	/**
	 * 닉네임
	 */
	@NotBlank(message = "Nickname is required.")
	private String nickname;

	/**
	 * 캐릭터 정보 (숫자 값으로 제공)
	 */
	@NotNull(message = "Character is required.")
	private Integer character;

	/**
	 * 지역 정보 (숫자 값으로 제공)
	 */
	@NotNull(message = "Region is required.")
	private Integer region;

	/**
	 * 레벨 정보 (숫자 값으로 제공)
	 */
	@NotNull(message = "Level is required.")
	private Integer level;
}