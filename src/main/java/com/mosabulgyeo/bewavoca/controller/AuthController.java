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

	@PostMapping("/check-device")
	public ResponseEntity<ApiResponse<UserProgressResponse>> checkDevice(@RequestBody @Valid DeviceCheckRequest request) {
		Optional<User> user = authService.findUserByDeviceId(request.getDeviceId());

		if (user.isPresent()) {
			UserProgressResponse progress = authService.getProgress(request.getDeviceId());
			return ResponseEntity.ok(new ApiResponse<>(
				"success",
				"User exists and progress retrieved",
				progress
			));
		}

		return ResponseEntity.ok(new ApiResponse<>("fail", "User does not exist", null));
	}

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<UserResponse>> signup(@RequestBody @Valid SignupRequest request) {
		User user = authService.registerUser(request.getDeviceId(), request.getNickname());
		return ResponseEntity.ok(new ApiResponse<>("success", "User registered successfully",
			new UserResponse(user.getId(), user.getNickname())));
	}

	@PatchMapping("/nickname")
	public ResponseEntity<ApiResponse<UserResponse>> updateNickname(@RequestBody @Valid UpdateNicknameRequest request) {
		User updatedUser = authService.updateNickname(request.getDeviceId(), request.getNewNickname());
		return ResponseEntity.ok(new ApiResponse<>("success", "Nickname updated successfully",
			new UserResponse(updatedUser.getId(), updatedUser.getNickname())));
	}
}