package com.mosabulgyeo.bewavoca.repository;

import com.mosabulgyeo.bewavoca.entity.Character;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CharacterRepository
 *
 * 캐릭터 데이터를 데이터베이스에서 관리하기 위한 JPA 레포지토리 인터페이스.
 * Character 엔티티와 매핑된 테이블에 접근하여 CRUD 작업을 수행.
 */
public interface CharacterRepository extends JpaRepository<Character, Long> {
}