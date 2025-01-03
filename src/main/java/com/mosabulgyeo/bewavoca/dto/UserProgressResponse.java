package com.mosabulgyeo.bewavoca.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProgressResponse {
	private Set<String> clearedStages; // 클리어한 스테이지 목록
	private Set<Integer> clearedRegions; // 클리어한 지역 목록

	public UserProgressResponse(Set<String> clearedStages, Set<Integer> clearedRegions) {
		this.clearedStages = clearedStages;
		this.clearedRegions = clearedRegions;
	}
}