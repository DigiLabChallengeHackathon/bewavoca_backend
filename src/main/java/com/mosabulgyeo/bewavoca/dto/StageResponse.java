package com.mosabulgyeo.bewavoca.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StageResponse {
	private Long id;          // 스테이지 ID
	private String name;      // 스테이지 이름
	private String description; // 스테이지 설명
	private int level;        // 스테이지 레벨
}