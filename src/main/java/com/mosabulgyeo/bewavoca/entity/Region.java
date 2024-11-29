package com.mosabulgyeo.bewavoca.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Region 엔티티 클래스
 * 게임에서 사용되는 지역 정보를 저장하고 관리하는 도메인 모델.
 * 지역 이름, 설명, 레벨, 그리고 해당 지역에 포함된 스테이지 유형(stage types)을 포함.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "region")
public class Region {

	/**
	 * 지역 고유 식별자.
	 * 데이터베이스에서 자동 생성되는 기본 키.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 지역 이름.
	 * null 허용되지 않음.
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 지역 설명.
	 * null 허용되지 않음.
	 */
	@Column(nullable = false)
	private String description;

	/**
	 * 지역 레벨(Level).
	 * 지역의 난이도 또는 순서를 나타냄.
	 * null 허용되지 않음.
	 */
	@Column(nullable = false)
	private int level;

	/**
	 * 해당 지역에 포함된 스테이지 유형(stage types).
	 * e.g., "OX Quiz", "Matching Game".
	 * 스테이지 유형은 문자열로 저장되며, Set을 사용하여 중복 방지.
	 */
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "region_stages", joinColumns = @JoinColumn(name = "region_id"))
	@Column(name = "stage_type")
	private Set<String> stageTypes;

	/**
	 * Region 엔티티 생성자.
	 * @param name 지역 이름
	 * @param description 지역 설명
	 * @param level 지역 레벨
	 * @param stageTypes 지역에 포함된 스테이지 유형
	 */
	public Region(String name, String description, int level, Set<String> stageTypes) {
		this.name = name;
		this.description = description;
		this.level = level;
		this.stageTypes = stageTypes;
	}
}