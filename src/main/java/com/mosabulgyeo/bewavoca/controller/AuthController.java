package com.mosabulgyeo.bewavoca.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mosabulgyeo.bewavoca.dto.*;
import com.mosabulgyeo.bewavoca.entity.User;
import com.mosabulgyeo.bewavoca.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	/**
	 * 기기 존재 여부 확인
	 * @param request 기기 ID
	 * @return 사용자 정보 또는 상태 메시지
	 */
	@PostMapping("/check-device")
	public ResponseEntity<ApiResponse<UserResponse>> checkDevice(@RequestBody @Valid DeviceRequest request) {
		Optional<User> user = authService.findUserByDeviceId(request.getDeviceId());

		if (user.isPresent()) {
			return ResponseEntity.ok(new ApiResponse<>(
				"success",
				"User exists",
				new UserResponse(user.get().getId(), user.get().getNickname())
			));
		}

		return ResponseEntity.ok(new ApiResponse<>(
			"fail",
			"User does not exist",
			null
		));
	}

	/**
	 * 회원가입 처리
	 * @param request 회원가입 요청 데이터
	 * @return 등록된 사용자 정보
	 */
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<UserResponse>> signup(@RequestBody @Valid SignupRequest request) {
		User user = authService.registerUser(request.getDeviceId(), request.getNickname());
		return ResponseEntity.ok(new ApiResponse<>(
			"success",
			"User registered successfully",
			new UserResponse(user.getId(), user.getNickname())
		));
	}

	/**
	 * 사용자 진행 상황 조회
	 * @param deviceId 사용자 기기 ID
	 * @return 진행 상황 데이터
	 */
	@GetMapping("/progress/{deviceId}")
	public ResponseEntity<ApiResponse<UserProgressResponse>> getProgress(@PathVariable String deviceId) {
		UserProgressResponse progress = authService.getProgress(deviceId);
		return ResponseEntity.ok(new ApiResponse<>(
			"success",
			"Progress retrieved successfully",
			progress
		));
	}

	/**
	 * 닉네임 업데이트
	 * @param request 닉네임 업데이트 요청 데이터
	 * @return 업데이트된 사용자 정보
	 */
	@PatchMapping("/nickname")
	public ResponseEntity<ApiResponse<UserResponse>> updateNickname(@RequestBody @Valid UpdateNicknameRequest request) {
		User updatedUser = authService.updateNickname(request.getDeviceId(), request.getNewNickname());
		return ResponseEntity.ok(new ApiResponse<>(
			"success",
			"Nickname updated successfully",
			new UserResponse(updatedUser.getId(), updatedUser.getNickname())
		));
	}
}