package com.mosabulgyeo.bewavoca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SignupRequest
 *
 * 클라이언트로부터 회원가입 요청 데이터를 받을 때 사용하는 DTO 클래스.
 * 디바이스 ID와 닉네임을 포함하며, 유효성 검증을 수행.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
	@NotBlank(message = "Device ID is required.")
	private String deviceId;

	@NotBlank(message = "Nickname is required.")
	@Size(max = 8, message = "Nickname cannot exceed 8 characters.")
	private String nickname;
}