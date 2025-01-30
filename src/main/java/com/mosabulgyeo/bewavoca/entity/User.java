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
	private Long selectedCharacterId = 1L;  // 기본값 하르방

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
}