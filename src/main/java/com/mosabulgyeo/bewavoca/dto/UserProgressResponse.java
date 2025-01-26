package com.mosabulgyeo.bewavoca.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProgressResponse {
	private Long userid;
	private String nickname;
	private Integer character;
	private Integer region;
	private Integer level;
	private Set<String> clearedStages;
	private Set<Integer> clearedRegions;
}