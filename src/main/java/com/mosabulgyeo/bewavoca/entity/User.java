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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String deviceId;

	@Column(nullable = false, length = 5)
	private String nickname;

	@Column(nullable = true)
	private Long selectedCharacterId = 1L;

	@ElementCollection
	@CollectionTable(name = "user_stages", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "stage")
	private Set<String> clearedStages = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "user_regions", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "region")
	private Set<Integer> clearedRegions = new HashSet<>();

	public void clearStage(int region, int stage) {
		clearedStages.add(region + "-" + stage);
		if (hasClearedAllStages(region)) {
			clearedRegions.add(region);
		}
	}

	public boolean hasClearedAllStages(int region) {
		return clearedStages.contains(region + "-1")
			&& clearedStages.contains(region + "-2")
			&& clearedStages.contains(region + "-3");
	}

	public boolean hasClearedRegion(int region) {
		return clearedRegions.contains(region);
	}

	public void setSelectedCharacterId(Long characterId) {
		if (characterId == null || characterId <= 0) {
			throw new IllegalArgumentException("Invalid character ID");
		}
		this.selectedCharacterId = characterId;
	}

	@Builder
	public User(String deviceId, String nickname, Long selectedCharacterId) {
		validateDeviceId(deviceId);
		validateNickname(nickname);
		this.deviceId = deviceId;
		this.nickname = nickname;
		this.selectedCharacterId = selectedCharacterId != null ? selectedCharacterId : 1L;  // 기본값 하르방
	}

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

	private void validateDeviceId(String deviceId) {
		if (deviceId == null || deviceId.trim().isEmpty()) {
			throw new IllegalArgumentException("Device ID cannot be null or empty");
		}
	}

	private static final int STAGE_COUNT_PER_REGION = 3;

	/**
	 * clearedStages에 "1-1", "1-2", "2-1" 등 형태로 저장
	 * "가장 늦게 깬 (region, stage)"를 찾아 다음 스테이지 계산
	 */
	public int getNextRegion() {
		if (clearedStages.isEmpty()) {
			// 아무것도 안 깼으면 region=1부터 시작
			return 1;
		}
		int maxRegion = 0;
		int maxStage = 0;
		for (String cleared : clearedStages) {
			String[] parts = cleared.split("-");
			int r = Integer.parseInt(parts[0]);
			int s = Integer.parseInt(parts[1]);

			// 지역이 더 큰 게 더 나중, 같으면 스테이지가 더 큰 게 더 나중
			if (r > maxRegion) {
				maxRegion = r;
				maxStage = s;
			} else if (r == maxRegion && s > maxStage) {
				maxStage = s;
			}
		}

		if (maxStage < STAGE_COUNT_PER_REGION) {
			// 현재 region에 스테이지 남음 → 지역 그대로
			return maxRegion;
		} else {
			// region을 다 깼으므로 다음 region
			return maxRegion + 1;
		}
	}

	public int getNextStage() {
		if (clearedStages.isEmpty()) {
			// 아무것도 안 깼으면 1-1부터
			return 1;
		}
		int maxRegion = 0;
		int maxStage = 0;
		for (String cleared : clearedStages) {
			String[] parts = cleared.split("-");
			int r = Integer.parseInt(parts[0]);
			int s = Integer.parseInt(parts[1]);
			if (r > maxRegion) {
				maxRegion = r;
				maxStage = s;
			} else if (r == maxRegion && s > maxStage) {
				maxStage = s;
			}
		}

		// 만약 현재 region 아직 다 못 깼으면 → stage+1
		// 다 깼으면 → 1
		if (maxStage < STAGE_COUNT_PER_REGION) {
			return maxStage + 1;
		} else {
			return 1; // 다음 region의 첫 스테이지
		}
	}
}