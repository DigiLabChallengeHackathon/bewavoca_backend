package com.mosabulgyeo.bewavoca.controller;

import com.mosabulgyeo.bewavoca.dto.ApiResponse;
import com.mosabulgyeo.bewavoca.dto.CharacterResponse;
import com.mosabulgyeo.bewavoca.dto.SelectCharacterRequest;
import com.mosabulgyeo.bewavoca.service.CharacterService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

/**
 * CharacterController
 *
 * 캐릭터 관련 API를 제공하는 컨트롤러 클래스.
 * 사용 가능한 캐릭터 조회, 캐릭터 선택, 선택된 캐릭터 정보 조회 기능을 포함.
 */
@RestController
@RequestMapping("/api/character")
public class CharacterController {

	private final CharacterService characterService;

	/**
	 * 생성자 의존성 주입
	 *
	 * @param characterService 캐릭터 서비스
	 */
	public CharacterController(CharacterService characterService) {
		this.characterService = characterService;
	}

	/**
	 * 사용 가능한 캐릭터 목록 조회
	 *
	 * 사용자의 디바이스 ID를 기반으로 해당 사용자가 잠금 해제 가능한 캐릭터 목록을 반환.
	 *
	 * @param deviceId 사용자 기기 고유 식별자
	 * @return 사용 가능한 캐릭터 목록
	 */
	@GetMapping("/{deviceId}")
	public ResponseEntity<ApiResponse<List<CharacterResponse>>> getAvailableCharacters(@PathVariable String deviceId) {
		try {
			List<CharacterResponse> characters = characterService.getAvailableCharacters(deviceId);
			return ResponseEntity.ok(new ApiResponse<>(
				"success",
				"Characters retrieved successfully.",
				characters
			));
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
				"fail",
				ex.getMessage(),
				null
			));
		}
	}

	/**
	 * 캐릭터 선택
	 *
	 * 사용자가 선택한 캐릭터 ID를 저장하고, 해당 캐릭터를 선택 상태로 설정.
	 *
	 * @param request 캐릭터 선택 요청 (디바이스 ID, 캐릭터 ID 포함)
	 * @return HTTP 200 OK 상태
	 */
	@PostMapping("/select")
	public ResponseEntity<ApiResponse<Void>> selectCharacter(@RequestBody @Valid SelectCharacterRequest request) {
		characterService.selectCharacter(request);
		return ResponseEntity.ok(new ApiResponse<>(
			"success",
			"Character selected successfully",
			null
		));
	}

	/** 선택된 캐릭터 정보 조회
	 *
	 * 사용자의 디바이스 ID를 기반으로 현재 선택된 캐릭터 정보를 반환.
	 *
	 * @param deviceId 사용자 기기 고유 식별자
	 * @return 선택된 캐릭터의 정보
	 */
	@GetMapping("/selected/{deviceId}")
	public ResponseEntity<ApiResponse<CharacterResponse>> getSelectedCharacter(@PathVariable String deviceId) {
		CharacterResponse selectedCharacter = characterService.getSelectedCharacter(deviceId);
		if (selectedCharacter == null) {
			throw new IllegalArgumentException("No selected character found for this device.");
		}
		return ResponseEntity.ok(new ApiResponse<>(
			"success",
			"Selected character retrieved",
			selectedCharacter
		));
	}
}