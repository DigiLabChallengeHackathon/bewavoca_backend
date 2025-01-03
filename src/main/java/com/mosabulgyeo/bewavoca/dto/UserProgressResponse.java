package com.mosabulgyeo.bewavoca.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProgressResponse {
	private Set<String> clearedStages;
	private Set<Integer> clearedRegions;

	public UserProgressResponse(Set<String> clearedStages, Set<Integer> clearedRegions) {
		this.clearedStages = clearedStages;
		this.clearedRegions = clearedRegions;
	}
}