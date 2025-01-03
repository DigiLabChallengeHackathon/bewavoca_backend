package com.mosabulgyeo.bewavoca.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterResponse {

	private Long id;
	private String name;
	private String description;
	private String dialogue;
	private String regionName;

	public CharacterResponse(Long id, String name, String description, String dialogue, String regionName) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.dialogue = dialogue;
		this.regionName = regionName;
	}
}