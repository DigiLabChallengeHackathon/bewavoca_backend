package com.mosabulgyeo.bewavoca.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	/**
	 * 사용자 고유 식별자 (자동 생성되는 기본 키)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 기기 고유 식별자 (유니크, null 불가)
	 */
	@Column(nullable = false, unique = true)
	private String deviceId;

	/**
	 * 사용자 닉네임 (최대 5자)
	 */
	@Column(nullable = false, length = 5)
	private String nickname;

	/**
	 * 선택된 캐릭터 ID (null 허용)
	 */
	@Column(nullable = true)
	private Long selectedCharacterId = 1L;

	/**
	 * 사용자가 클리어한 스테이지 목록 ("region-stage" 형태)
	 */
	@ElementCollection
	@CollectionTable(name = "user_stages", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "stage")
	private Set<String> clearedStages = new HashSet<>();

	/**
	 * 사용자가 완료한 지역 목록 (지역 번호)
	 */
	@ElementCollection
	@CollectionTable(name = "user_regions", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "region")
	private Set<Integer> clearedRegions = new HashSet<>();

	/**
	 * 스테이지 클리어 처리
	 * @param region 지역 번호
	 * @param stage 스테이지 번호
	 */
	public void clearStage(int region, int stage) {
		clearedStages.add(region + "-" + stage);

		// 해당 지역의 모든 스테이지를 클리어한 경우 지역도 클리어로 설정
		if (hasClearedAllStages(region)) {
			clearedRegions.add(region);
		}
	}

	/**
	 * 특정 지역의 모든 스테이지 클리어 여부 확인
	 * @param region 지역 번호
	 * @return 모든 스테이지를 클리어했으면 true
	 */
	public boolean hasClearedAllStages(int region) {
		return clearedStages.contains(region + "-1")
			&& clearedStages.contains(region + "-2")
			&& clearedStages.contains(region + "-3");
	}

	/**
	 * 선택된 캐릭터 ID 업데이트
	 * @param characterId 선택할 캐릭터 ID
	 */
	public void setSelectedCharacterId(Long characterId) {
		if (characterId == null || characterId <= 0) {
			throw new IllegalArgumentException("Invalid character ID");
		}
		this.selectedCharacterId = characterId;
	}

	/**
	 * User 생성자
	 * @param deviceId 기기 고유 식별자
	 * @param nickname 사용자 닉네임
	 */
	@Builder
	public User(String deviceId, String nickname) {
		validateDeviceId(deviceId);
		validateNickname(nickname);

		this.deviceId = deviceId;
		this.nickname = nickname;
		this.selectedCharacterId = 1L;
	}

	/**
	 * 닉네임 유효성 검증
	 * @param nickname 검증할 닉네임
	 */
	private void validateNickname(String nickname) {
		if (nickname == null || nickname.trim().isEmpty()) {
			throw new IllegalArgumentException("Nickname cannot be null or empty");
		}
		if (nickname.length() > 5) {
			throw new IllegalArgumentException("Nickname cannot exceed 5 characters");
		}
	}

	public void updateNickname(String newNickname) {
		validateNickname(newNickname);
		this.nickname = newNickname;
	}

	/**
	 * 디바이스 ID 유효성 검증
	 * @param deviceId 검증할 디바이스 ID
	 */
	private void validateDeviceId(String deviceId) {
		if (deviceId == null || deviceId.trim().isEmpty()) {
			throw new IllegalArgumentException("Device ID cannot be null or empty");
		}
	}
}