package com.mosabulgyeo.bewavoca.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CharacterResponse
 *
 * 캐릭터 정보를 클라이언트에 반환하기 위한 DTO 클래스.
 * 캐릭터의 이름, 설명, 외형, 잠금 해제 레벨, 잠금 해제 여부를 포함.
 */
@Getter
@AllArgsConstructor
public class CharacterResponse {
	private Long id;              // 캐릭터 고유 ID
	private String name;          // 캐릭터 이름
	private String description;   // 캐릭터 설명
	private String dialogue;
	private Map<String, String> appearance;    // 캐릭터 외형 (이미지 경로 또는 URL)
	private StageResponse stage;
}