package com.mosabulgyeo.bewavoca.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 엔티티 클래스
 * 애플리케이션에서 사용자 정보를 저장하고 관리하는 도메인 모델
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	/**
	 * 사용자 고유 식별자
	 * 데이터베이스에서 자동 생성되는 기본 키
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 기기 고유 식별자
	 * 사용자의 디바이스를 고유하게 식별하는 값
	 * 중복 불가, null 허용 안됨
	 */
	@Column(nullable = false, unique = true)
	private String deviceId; // iPad 기기 정보 (고유값)

	/**
	 * 사용자 닉네임
	 * 최대 8자까지 허용, null 불가
	 */
	@Column(nullable = false, length = 8)
	private String nickname; // 닉네임 (8자 제한)

	/**
	 * 선택된 캐릭터 ID
	 * 사용자가 선택한 캐릭터를 나타냅니다.
	 * 선택되지 않은 경우 null.
	 */
	@Column(nullable = true)
	private Long selectedCharacterId;

	@ManyToMany
	@JoinTable(
		name = "user_cleared_mini_stages",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "mini_stage_id")
	)
	private Set<MiniStage> clearedMiniStages = new HashSet<>();

	/**
	 * 특정 미니 스테이지 완료 여부 확인
	 *
	 * @param miniStageId 확인할 미니 스테이지 ID
	 * @return 완료 여부
	 */
	public boolean hasClearedMiniStage(Long miniStageId) {
		return clearedMiniStages.stream().anyMatch(miniStage -> miniStage.getId().equals(miniStageId));
	}

	/**
	 * 특정 미니 스테이지를 완료 처리
	 *
	 * @param miniStage 완료한 미니 스테이지
	 */
	public void clearMiniStage(MiniStage miniStage) {
		this.clearedMiniStages.add(miniStage);
	}

	/**
	 * 특정 Stage의 모든 미니 스테이지를 완료했는지 확인
	 *
	 * @param stage 완료 확인할 Stage
	 * @return 완료 여부
	 */
	public boolean hasClearedStage(Stage stage) {
		return stage.getMiniStages().stream()
			.allMatch(miniStage -> hasClearedMiniStage(miniStage.getId()));
	}

	/**
	 * 선택된 캐릭터 ID를 업데이트하는 메서드
	 *
	 * 주어진 캐릭터 ID를 사용하여 현재 사용자가 선택한 캐릭터를 설정합니다.
	 *
	 * @param characterId 선택할 캐릭터 ID
	 * @throws IllegalArgumentException 유효하지 않은 캐릭터 ID일 경우 예외 발생
	 */
	public void setSelectedCharacterId(Long characterId) {
		if (characterId == null || characterId <= 0) {
			throw new IllegalArgumentException("Invalid character ID");
		}
		this.selectedCharacterId = characterId;
	}

	/**
	 * 사용자 생성자
	 *
	 * @param deviceId 기기 고유 식별자
	 * @param nickname 사용자 닉네임
	 */
	@Builder
	public User(String deviceId, String nickname) {
		validateDeviceId(deviceId);
		validateNickname(nickname);

		this.deviceId = deviceId;
		this.nickname = nickname;
	}

	/**
	 * 닉네임 업데이트 메서드
	 * 비즈니스 로직을 포함한 안전한 업데이트
	 *
	 * @param newNickname 새로운 닉네임
	 */
	public void updateNickname(String newNickname) {
		validateNickname(newNickname);
		this.nickname = newNickname;
	}

	/**
	 * 닉네임 유효성 검증
	 *
	 * @param nickname 검증할 닉네임
	 * @throws IllegalArgumentException 닉네임이 유효하지 않을 경우
	 */
	private void validateNickname(String nickname) {
		if (nickname == null || nickname.trim().isEmpty()) {
			throw new IllegalArgumentException("Nickname cannot be null or empty");
		}
		if (nickname.length() > 8) {
			throw new IllegalArgumentException("Nickname cannot exceed 8 characters");
		}
	}

	/**
	 * 디바이스 ID 유효성 검증
	 *
	 * @param deviceId 검증할 디바이스 ID
	 * @throws IllegalArgumentException 디바이스 ID가 유효하지 않을 경우
	 */
	private void validateDeviceId(String deviceId) {
		if (deviceId == null || deviceId.trim().isEmpty()) {
			throw new IllegalArgumentException("Device ID cannot be null or empty");
		}
	}
}