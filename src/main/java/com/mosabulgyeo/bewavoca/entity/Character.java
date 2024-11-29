package com.mosabulgyeo.bewavoca.entity;

import java.awt.*;
import java.util.Map;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "jeju_character")
public class Character {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String dialogue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stage_id", nullable = false)
	private Stage stage;

	@ElementCollection
	@CollectionTable(name = "character_appearances", joinColumns = @JoinColumn(name = "character_id"))
	@MapKeyColumn(name = "expression")
	@Column(name = "appearance")
	private Map<String, String> appearances;

	public Character(String name, String description, String dialogue, Stage stage, Map<String, String> appearances) {
		this.name = name;
		this.description = description;
		this.dialogue = dialogue;
		this.stage = stage;
		this.appearances = appearances;
	}

	/**
	 * 특정 표정에 해당하는 appearance 반환
	 *
	 * @param expression 표정 (e.g., "happy", "sad")
	 * @return 해당 표정에 대한 appearance 경로
	 */
	public String getAppearanceByExpression(String expression) {
		return appearances.getOrDefault(expression, "default.png");
	}

}