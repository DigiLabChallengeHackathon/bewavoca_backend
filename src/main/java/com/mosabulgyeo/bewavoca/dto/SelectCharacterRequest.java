package com.mosabulgyeo.bewavoca.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * SelectCharacterRequest
 *
 * 클라이언트로부터 캐릭터 선택 요청을 받을 때 사용하는 DTO 클래스.
 * 디바이스 ID와 선택할 캐릭터 ID를 포함.
 */
@Getter
@Setter
public class SelectCharacterRequest {
	@NotNull(message = "Device ID is required.")
	private String deviceId;

	@NotNull(message = "Character ID is required.")
	@Min(value = 1, message = "Character ID must be a positive number.")
	private Long characterId;
}