package com.mosabulgyeo.bewavoca.entity;

import java.util.Map;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Character 엔티티 클래스
 * 게임에서 제공되는 캐릭터 정보를 저장하고 관리하는 도메인 모델.
 * 캐릭터의 이름, 설명, 대화 내용, 소속된 지역(region), 표정에 따른 appearance를 포함.
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "jeju_character")
public class Character {

	/**
	 * 캐릭터 고유 식별자.
	 * 데이터베이스에서 자동 생성되는 기본 키.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 캐릭터 이름.
	 * null 허용되지 않음.
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 캐릭터 설명.
	 * null 허용되지 않음.
	 */
	@Column(nullable = false)
	private String description;

	/**
	 * 캐릭터 대사(Dialogue).
	 * null 허용되지 않음.
	 */
	@Column(nullable = false, columnDefinition = "TEXT")
	private String dialogue;

	/**
	 * 캐릭터가 속한 지역(Region).
	 * 다대일 관계로 설정되어 있으며, 캐릭터는 하나의 지역에 소속된다.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", nullable = false)
	private Region region;

	/**
	 * 캐릭터의 표정(Expression)과 해당 appearance(이미지 경로) 매핑 정보.
	 * e.g., "happy" -> "happy.png".
	 * 표정-appearance를 다루는 맵 구조로 저장.
	 */
	@ElementCollection
	@CollectionTable(name = "character_appearances", joinColumns = @JoinColumn(name = "character_id"))
	@MapKeyColumn(name = "expression")
	@Column(name = "appearance")
	private Map<String, String> appearances;

	/**
	 * 캐릭터 엔티티 생성자.
	 * @param name 캐릭터 이름
	 * @param description 캐릭터 설명
	 * @param dialogue 캐릭터 대화 내용
	 * @param region 캐릭터가 속한 지역
	 * @param appearances 캐릭터의 표정과 appearance 매핑 정보
	 */
	public Character(String name, String description, String dialogue, Region region, Map<String, String> appearances) {
		this.name = name;
		this.description = description;
		this.dialogue = dialogue;
		this.region = region;
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