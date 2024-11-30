package com.mosabulgyeo.bewavoca.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mosabulgyeo.bewavoca.entity.Character;
import com.mosabulgyeo.bewavoca.entity.User;
import com.mosabulgyeo.bewavoca.repository.CharacterRepository;
import com.mosabulgyeo.bewavoca.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * 사용자 인증 서비스
 * 사용자 등록, 로그인 로직을 처리하는 서비스 계층
 */
@Service
public class AuthService {

	private final UserRepository userRepository;
	private final CharacterRepository characterRepository; // CharacterRepository 추가

	/**
	 * 생성자 의존성 주입
	 *
	 * @param userRepository 사용자 정보 저장소
	 * @param characterRepository 캐릭터 정보 저장소
	 */
	public AuthService(UserRepository userRepository, CharacterRepository characterRepository) {
		this.userRepository = userRepository;
		this.characterRepository = characterRepository;
	}

	/**
	 * 기기 ID로 사용자 검색
	 *
	 * 데이터베이스에서 주어진 기기 ID를 기준으로 사용자를 검색합니다.
	 * 사용자가 존재하지 않을 경우 빈 Optional 객체를 반환합니다.
	 *
	 * @param deviceId 검색할 기기 고유 식별자
	 * @return Optional로 감싸진 사용자 객체 (존재하지 않을 경우 빈 Optional)
	 */
	public Optional<User> findUserByDeviceId(String deviceId) {
		return userRepository.findByDeviceId(deviceId);
	}

	/**
	 * 새로운 사용자 등록
	 * 주어진 기기 ID와 닉네임을 사용하여 새로운 사용자 정보를 등록합니다.
	 * 기본 캐릭터(ID 1번)를 자동으로 추가합니다.
	 *
	 * @param deviceId 기기 고유 식별자
	 * @param nickname 사용자 닉네임
	 * @return 등록된 사용자 객체
	 * @throws IllegalArgumentException 기기 ID가 이미 등록된 경우 예외 발생
	 */
	@Transactional
	public User registerUser(String deviceId, String nickname) {
		if (userRepository.findByDeviceId(deviceId).isPresent()) {
			throw new IllegalArgumentException("Device already registered.");
		}

		// 기본 캐릭터(ID 1번) 조회
		Character defaultCharacter = characterRepository.findById(1L)
			.orElseThrow(() -> new IllegalStateException("Default character (ID 1) not found"));

		// 사용자 생성
		User newUser = User.builder()
			.deviceId(deviceId)
			.nickname(nickname)
			.build();

		// 기본 캐릭터 설정
		newUser.setSelectedCharacterId(defaultCharacter.getId());

		return userRepository.save(newUser);
	}

	/**
	 * 기기번호 기반 사용자 정보 조회
	 *
	 * @param deviceId 조회할 기기 고유 식별자
	 * @return 조회된 사용자 객체
	 * @throws IllegalArgumentException 등록되지 않은 기기번호일 경우
	 */
	public User getUserByDeviceId(String deviceId) {
		return userRepository.findByDeviceId(deviceId)
			.orElseThrow(() -> new IllegalArgumentException("Device is not registered."));
	}

	/**
	 * 사용자 닉네임 업데이트 처리
	 * @param deviceId 기기 고유 식별자
	 * @param newNickname 변경할 닉네임
	 * @return 업데이트된 사용자 객체
	 */
	@Transactional
	public User updateNickname(String deviceId, String newNickname) {
		User user = getUserByDeviceId(deviceId);
		user.updateNickname(newNickname);
		return user;
	}
}