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
	 * 캐릭터 엔티티 생성자.
	 * @param name 캐릭터 이름
	 * @param description 캐릭터 설명
	 * @param dialogue 캐릭터 대화 내용
	 * @param region 캐릭터가 속한 지역
	 */
	public Character(String name, String description, String dialogue, Region region) {
		this.name = name;
		this.description = description;
		this.dialogue = dialogue;
		this.region = region;
	}
}