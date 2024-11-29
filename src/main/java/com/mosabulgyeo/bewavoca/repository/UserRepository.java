package com.mosabulgyeo.bewavoca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mosabulgyeo.bewavoca.entity.User;

/**
 * UserRepository
 *
 * 사용자 정보를 데이터베이스에서 관리하기 위한 JPA 레포지토리 인터페이스
 * User 엔티티와 매핑된 테이블에 접근하여 CRUD 작업을 수행
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * 기기 ID로 사용자 정보를 검색
	 *
	 * @param deviceId 검색할 기기 고유 식별자
	 * @return Optional로 감싸진 사용자 객체
	 */
	Optional<User> findByDeviceId(String deviceId);
}