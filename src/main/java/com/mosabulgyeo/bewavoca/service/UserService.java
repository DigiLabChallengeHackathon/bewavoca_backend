package com.mosabulgyeo.bewavoca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mosabulgyeo.bewavoca.entity.Region;
import com.mosabulgyeo.bewavoca.entity.User;
import com.mosabulgyeo.bewavoca.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * 사용자의 클리어된 지역 ID 목록을 반환.
	 *
	 * @param deviceId 사용자 기기 ID
	 * @return 클리어된 지역 ID 목록
	 */
	public List<Long> getClearedRegionIdsByDeviceId(String deviceId) {
		User user = userRepository.findByDeviceId(deviceId)
			.orElseThrow(() -> new IllegalArgumentException("User not found with deviceId: " + deviceId));
		return user.getCompletedRegions().stream().toList();
	}

	/**
	 * 사용자가 특정 지역을 클리어했는지 확인.
	 *
	 * @param deviceId 사용자 기기 ID
	 * @param regionId 지역 ID
	 * @return 클리어 여부
	 */
	public boolean userHasClearedRegion(String deviceId, Long regionId) {
		User user = userRepository.findByDeviceId(deviceId)
			.orElseThrow(() -> new IllegalArgumentException("User not found with deviceId: " + deviceId));
		return user.hasClearedRegion(regionId);
	}
}