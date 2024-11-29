package com.mosabulgyeo.bewavoca.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 공통 응답 포맷을 정의하는 DTO 클래스
 * @param <T> 응답 데이터 타입
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
	private String status;  // "success" or "error"
	private String message; // 응답 메시지
	private T data;         // 응답 데이터
}