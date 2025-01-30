package com.mosabulgyeo.bewavoca.service;

import java.util.Optional;

import com.mosabulgyeo.bewavoca.dto.UserProgressResponse;
import com.mosabulgyeo.bewavoca.entity.User;
import com.mosabulgyeo.bewavoca.entity.Character;
import com.mosabulgyeo.bewavoca.repository.UserRepository;
import com.mosabulgyeo.bewavoca.repository.CharacterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final CharacterRepository characterRepository;

	public AuthService(UserRepository userRepository, CharacterRepository characterRepository) {
		this.userRepository = userRepository;
		this.characterRepository = characterRepository;
	}

	public Optional<User> findUserByDeviceId(String deviceId) {
		return userRepository.findByDeviceId(deviceId);
	}

	/**
	 * 사용자 등록 시 하르방 자동 선택
	 */
	@Transactional
	public User registerUser(String deviceId, String nickname) {
		if (userRepository.findByDeviceId(deviceId).isPresent()) {
			throw new IllegalArgumentException("Device already registered.");
		}

		Character harbang = characterRepository.findById(1L)
			.orElseThrow(() -> new IllegalStateException("Default character (하르방) not found"));

		User newUser = User.builder()
			.deviceId(deviceId)
			.nickname(nickname)
			.selectedCharacterId(harbang.getId())
			.build();

		return userRepository.save(newUser);
	}

	@Transactional
	public void clearStage(String deviceId, int region, int stage) {
		User user = userRepository.findByDeviceId(deviceId)
			.orElseThrow(() -> new IllegalArgumentException("User not found."));
		user.clearStage(region, stage);
	}

	public UserProgressResponse getProgress(String deviceId) {
		User user = userRepository.findByDeviceId(deviceId)
			.orElseThrow(() -> new IllegalArgumentException("User not found."));

		Integer region = user.getClearedRegions().stream()
			.max(Integer::compareTo)
			.orElse(0);

		Integer level = user.getClearedStages().stream()
			.map(stage -> Integer.parseInt(stage.split("-")[1]))
			.max(Integer::compareTo)
			.orElse(0);

		return new UserProgressResponse(
			user.getId(),
			user.getNickname(),
			user.getSelectedCharacterId(),
			region,
			level,
			user.getClearedStages(),
			user.getClearedRegions()
		);
	}

	@Transactional
	public User updateNickname(String deviceId, String newNickname) {
		User user = userRepository.findByDeviceId(deviceId)
			.orElseThrow(() -> new IllegalArgumentException("User not found."));
		user.updateNickname(newNickname);
		return user;
	}
}