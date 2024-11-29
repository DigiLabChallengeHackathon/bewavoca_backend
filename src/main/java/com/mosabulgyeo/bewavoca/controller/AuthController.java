package com.mosabulgyeo.bewavoca.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mosabulgyeo.bewavoca.dto.DeviceRequest;
import com.mosabulgyeo.bewavoca.dto.SignupRequest;
import com.mosabulgyeo.bewavoca.dto.UpdateNicknameRequest;
import com.mosabulgyeo.bewavoca.dto.UserResponse;
import com.mosabulgyeo.bewavoca.entity.User;
import com.mosabulgyeo.bewavoca.service.AuthService;

/**
 * 사용자 인증 REST 컨트롤러
 * 회원가입 및 로그인 엔드포인트 제공
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	/**
	 * 생성자 의존성 주입
	 *
	 * @param authService 사용자 인증 서비스
	 */
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	/**
	 * 기기 존재 여부 확인 엔드포인트
	 *
	 * 클라이언트에서 제공한 기기 ID를 사용하여 사용자의 존재 여부를 확인합니다.
	 * 사용자가 존재할 경우 사용자 정보를 반환하며, 존재하지 않을 경우 상태와 메시지를 반환합니다.
	 *
	 * @param request 기기 ID를 포함한 요청 데이터
	 * @return 사용자 정보 또는 상태 메시지를 포함한 ResponseEntity
	 */
	@PostMapping("/check-device")
	public ResponseEntity<?> checkDevice(@RequestBody DeviceRequest request) {
		Optional<User> user = authService.findUserByDeviceId(request.getDeviceId());

		if (user.isPresent()) {
			User existingUser = user.get();
			return ResponseEntity.ok(new UserResponse(
				existingUser.getId(),
				existingUser.getNickname()
			));
		}

		return ResponseEntity.ok(Map.of("status", "not_exists", "message", "User does not exist."));
	}

	/**
	 * 회원가입 처리 엔드포인트
	 *
	 * 클라이언트에서 제공한 기기 ID와 닉네임을 사용하여 새로운 사용자를 등록합니다.
	 * 등록이 완료되면 사용자 정보를 반환합니다.
	 *
	 * @param request 기기 ID와 닉네임을 포함한 요청 데이터
	 * @return 등록된 사용자 정보를 포함한 ResponseEntity
	 */
	@PostMapping("/signup")
	public ResponseEntity<UserResponse> signup(@RequestBody SignupRequest request) {
		User user = authService.registerUser(request.getDeviceId(), request.getNickname());
		return ResponseEntity.ok(new UserResponse(
			user.getId(),
			user.getNickname()
		));
	}

	/**
	 * 기기번호로 사용자 정보 조회 엔드포인트
	 *
	 * 제공된 기기 ID로 사용자 정보 검색
	 * HTTP 200 OK 응답과 함께 사용자 정보 반환
	 *
	 * @param deviceId 조회할 기기 고유 식별자
	 * @return 사용자 정보를 포함한 ResponseEntity
	 */
	@GetMapping("/user/{deviceId}")
	public ResponseEntity<UserResponse> getUserInfo(@PathVariable String deviceId) {
		User user = authService.getUserByDeviceId(deviceId);
		return ResponseEntity.ok(new UserResponse(user.getId(), user.getNickname()));
	}

	/**
	 * 닉네임 업데이트 엔드포인트
	 * @param request 닉네임 업데이트 요청 데이터
	 * @return 업데이트된 사용자 정보
	 */
	@PatchMapping("/nickname")
	public ResponseEntity<UserResponse> updateNickname(@RequestBody UpdateNicknameRequest request) {
		User updatedUser = authService.updateNickname(request.getDeviceId(), request.getNewNickname());
		return ResponseEntity.ok(new UserResponse(updatedUser.getId(), updatedUser.getNickname()));
	}
}