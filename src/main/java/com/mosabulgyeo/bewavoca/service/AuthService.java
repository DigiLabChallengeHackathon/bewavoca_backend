package com.mosabulgyeo.bewavoca.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.mosabulgyeo.bewavoca.dto.UserProgressResponse;
import com.mosabulgyeo.bewavoca.entity.User;
import com.mosabulgyeo.bewavoca.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class AuthService {

	private final UserRepository userRepository;

	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Optional<User> findUserByDeviceId(String deviceId) {
		return userRepository.findByDeviceId(deviceId);
	}

	@Transactional
	public User registerUser(String deviceId, String nickname) {
		if (userRepository.findByDeviceId(deviceId).isPresent()) {
			throw new IllegalArgumentException("Device already registered.");
		}
		User newUser = User.builder().deviceId(deviceId).nickname(nickname).build();
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
		return new UserProgressResponse(
			user.getId(),
			user.getNickname(),
			1, // default character
			0, // default region
			0, // default level
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