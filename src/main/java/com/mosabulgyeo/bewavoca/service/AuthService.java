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

	/**
	 * 기기 ID로 사용자 검색
	 * @param deviceId 기기 ID
	 * @return Optional로 감싸진 User 객체
	 */
	public Optional<User> findUserByDeviceId(String deviceId) {
		return userRepository.findByDeviceId(deviceId);
	}

	/**
	 * 새로운 사용자 등록
	 * @param deviceId 사용자 기기 ID
	 * @param nickname 사용자 닉네임
	 * @return 등록된 사용자
	 */
	@Transactional
	public User registerUser(String deviceId, String nickname) {
		if (userRepository.findByDeviceId(deviceId).isPresent()) {
			throw new IllegalArgumentException("Device already registered.");
		}

		User newUser = User.builder()
			.deviceId(deviceId)
			.nickname(nickname)
			.build();

		return userRepository.save(newUser);
	}

	/**
	 * 스테이지 클리어 처리
	 * @param deviceId 사용자 기기 ID
	 * @param region 지역 번호
	 * @param stage 스테이지 번호
	 */
	@Transactional
	public void clearStage(String deviceId, int region, int stage) {
		User user = userRepository.findByDeviceId(deviceId)
			.orElseThrow(() -> new IllegalArgumentException("User not found."));
		user.clearStage(region, stage);
	}

	/**
	 * 사용자 진행 상황 조회
	 * @param deviceId 사용자 기기 ID
	 * @return 사용자 진행 상황
	 */
	public UserProgressResponse getProgress(String deviceId) {
		User user = userRepository.findByDeviceId(deviceId)
			.orElseThrow(() -> new IllegalArgumentException("User not found."));

		return new UserProgressResponse(user.getClearedStages(), user.getClearedRegions());
	}

	/**
	 * 닉네임 업데이트
	 * @param deviceId 사용자 기기 ID
	 * @param newNickname 새로운 닉네임
	 * @return 업데이트된 User 객체
	 */
	@Transactional
	public User updateNickname(String deviceId, String newNickname) {
		User user = userRepository.findByDeviceId(deviceId)
			.orElseThrow(() -> new IllegalArgumentException("User not found."));
		user.updateNickname(newNickname);
		return user;
	}
}