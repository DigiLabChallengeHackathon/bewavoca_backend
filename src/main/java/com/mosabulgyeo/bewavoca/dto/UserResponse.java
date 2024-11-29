package com.mosabulgyeo.bewavoca.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * UserResponse
 *
 * 사용자 정보를 클라이언트로 반환하기 위한 DTO 클래스
 * API 응답에서 사용자 데이터와 메시지를 전달
 */
@Getter
@Setter
public class UserResponse {

	/** 사용자 고유 식별자 */
	private Long userId;

	/** 사용자 닉네임 */
	private String nickname;

	/**
	 * UserResponse 생성자
	 *
	 * @param userId 사용자 고유 식별자
	 * @param nickname 사용자 닉네임
	 */
	public UserResponse(Long userId, String nickname) {
		this.userId = userId;
		this.nickname = nickname;
	}
}