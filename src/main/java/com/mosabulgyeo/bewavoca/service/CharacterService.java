package com.mosabulgyeo.bewavoca.service;

import com.mosabulgyeo.bewavoca.dto.CharacterResponse;
import com.mosabulgyeo.bewavoca.dto.SelectCharacterRequest;
import com.mosabulgyeo.bewavoca.entity.Character;
import com.mosabulgyeo.bewavoca.entity.User;
import com.mosabulgyeo.bewavoca.repository.CharacterRepository;
import com.mosabulgyeo.bewavoca.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterService {

	private final CharacterRepository characterRepository;
	private final UserRepository userRepository;

	/**
	 * CharacterService 생성자
	 * 캐릭터와 사용자 저장소 의존성을 주입받아 초기화.
	 *
	 * @param characterRepository 캐릭터 데이터 접근 레포지토리
	 * @param userRepository 사용자 데이터 접근 레포지토리
	 */
	public CharacterService(CharacterRepository characterRepository, UserRepository userRepository) {
		this.characterRepository = characterRepository;
		this.userRepository = userRepository;
	}

	/**
	 * 사용자가 잠금 해제 가능한 캐릭터 목록 조회
	 *
	 * @param deviceId 사용자 기기 ID
	 * @return 잠금 해제 가능한 캐릭터 목록
	 * @throws IllegalArgumentException 사용자가 존재하지 않을 경우
	 */
	public List<CharacterResponse> getAvailableCharacters(String deviceId) {
		// 사용자 확인
		User user = userRepository.findByDeviceId(deviceId)
			.orElseThrow(() -> new IllegalArgumentException("User not found for this device."));

		// 기본 캐릭터(ID 1번) 조회
		Character defaultCharacter = characterRepository.findById(1L)
			.orElseThrow(() -> new IllegalStateException("Default character (ID 1) not found"));

		// 사용자가 클리어한 지역에 따라 잠금 해제된 캐릭터 필터링
		List<Character> unlockedCharacters = characterRepository.findAll().stream()
			.filter(character -> user.hasClearedRegion(character.getRegion().getId()))
			.collect(Collectors.toList());

		// 기본 캐릭터(ID 1번)를 추가
		if (unlockedCharacters.stream().noneMatch(character -> character.getId().equals(defaultCharacter.getId()))) {
			unlockedCharacters.add(defaultCharacter);
		}

		// 캐릭터 리스트를 DTO로 변환하여 반환
		return unlockedCharacters.stream()
			.map(character -> new CharacterResponse(
				character.getId(),
				character.getName(),
				character.getDescription(),
				character.getDialogue(),
				character.getAppearances(),
				character.getRegion().getName()
			))
			.collect(Collectors.toList());
	}

	/**
	 * 캐릭터 선택 기능
	 * 사용자가 특정 캐릭터를 선택하면 해당 캐릭터를 설정.
	 *
	 * @param request 선택 요청 데이터 (기기 ID, 캐릭터 ID 포함)
	 * @throws IllegalArgumentException 사용자 또는 캐릭터가 존재하지 않거나 캐릭터 잠금 해제가 되지 않았을 경우
	 */
	/**
	 * 캐릭터 선택 기능
	 * 사용자가 특정 캐릭터를 선택하면 해당 캐릭터를 설정.
	 *
	 * @param request 선택 요청 데이터 (기기 ID, 캐릭터 ID 포함)
	 * @throws IllegalArgumentException 사용자 또는 캐릭터가 존재하지 않을 경우
	 */
	@Transactional
	public void selectCharacter(SelectCharacterRequest request) {
		User user = userRepository.findByDeviceId(request.getDeviceId())
			.orElseThrow(() -> new IllegalArgumentException("User not found"));

		Character character = characterRepository.findById(request.getCharacterId())
			.orElseThrow(() -> new IllegalArgumentException("Character not found"));

		user.setSelectedCharacterId(character.getId());
		userRepository.save(user);
	}

	/**
	 * 선택된 캐릭터 정보 조회
	 *
	 * @param deviceId 사용자 디바이스 ID
	 * @return 선택된 캐릭터의 정보
	 */
	public CharacterResponse getSelectedCharacter(String deviceId) {
		User user = userRepository.findByDeviceId(deviceId)
			.orElseThrow(() -> new IllegalArgumentException("User not found"));

		Long selectedCharacterId = user.getSelectedCharacterId();
		if (selectedCharacterId == null) {
			throw new IllegalArgumentException("No character selected for this user.");
		}

		Character character = characterRepository.findById(selectedCharacterId)
			.orElseThrow(() -> new IllegalArgumentException("Selected character not found"));

		return new CharacterResponse(
			character.getId(),
			character.getName(),
			character.getDescription(),
			character.getDialogue(),
			character.getAppearances(),
			character.getRegion().getName()
		);
	}
}
