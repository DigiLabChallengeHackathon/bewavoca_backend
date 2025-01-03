package com.mosabulgyeo.bewavoca.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * CharacterResponse 클래스
 * 캐릭터 정보를 반환하기 위한 DTO 클래스.
 */
@Getter
@Setter
@AllArgsConstructor
public class CharacterResponse {

	/**
	 * 캐릭터 고유 ID.
	 */
	private Long id;

	/**
	 * 캐릭터 이름.
	 * 예: "제주도민 캐릭터"
	 */
	private String name;

	/**
	 * 캐릭터 설명.
	 * 캐릭터의 간단한 소개를 포함.
	 */
	private String description;

	/**
	 * 캐릭터 대사.
	 * 예: "안녕하세요! 제주도에 오신 것을 환영합니다."
	 */
	private String dialogue;

	/**
	 * 캐릭터가 속한 지역 이름.
	 * 예: "제주시", "서귀포시".
	 */
	private String regionName;
}