package com.mosabulgyeo.bewavoca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * UpdateNicknameRequest
 *
 * 닉네임 변경 요청 데이터를 캡슐화하는 DTO 클래스
 * 클라이언트에서 보내는 데이터를 유효성 검증과 함께 처리
 */
@Getter
@Setter
public class UpdateNicknameRequest {

	/**
	 * 기기 고유 식별자
	 * 사용자를 식별하기 위한 값으로 필수 입력
	 */
	@NotBlank(message = "Device ID is required.")
	private String deviceId;

	/**
	 * 새로운 닉네임
	 * 닉네임은 최대 8자까지 허용하며 필수 입력
	 */
	@NotBlank(message = "New nickname is required.")
	@Size(max = 8, message = "Nickname cannot exceed 8 characters.")
	private String newNickname;
}