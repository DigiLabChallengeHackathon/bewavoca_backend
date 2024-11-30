package com.mosabulgyeo.bewavoca.exception;

import com.mosabulgyeo.bewavoca.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler
 *
 * 컨트롤러 전역에서 발생하는 예외를 처리하는 클래스.
 * ApiResponse 포맷으로 에러 응답을 반환하여 일관성을 유지.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * IllegalArgumentException 처리
	 *
	 * @param ex IllegalArgumentException 예외
	 * @return 공통 응답 포맷으로 처리된 에러 응답
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new ApiResponse<>(
				"fail",
				ex.getMessage(),
				null
			));
	}

	/**
	 * RuntimeException 처리
	 *
	 * @param ex RuntimeException 예외
	 * @return 공통 응답 포맷으로 처리된 에러 응답
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
			"error",
			"Unexpected error occurred: " + ex.getMessage(),
			null
		));
	}

	/**
	 * 모든 예외 처리 (Fallback)
	 *
	 * @param ex Exception 예외
	 * @return 공통 응답 포맷으로 처리된 에러 응답
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
			"error",
			"An unexpected error occurred. Please try again later.",
			null
		));
	}
}