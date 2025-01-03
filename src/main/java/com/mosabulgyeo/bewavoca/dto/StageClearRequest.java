package com.mosabulgyeo.bewavoca.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StageClearRequest {
	private String deviceId; // 사용자 ID
	private int region;      // 지역 번호
	private int stage;       // 스테이지 번호
}