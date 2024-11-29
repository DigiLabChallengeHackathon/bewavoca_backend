package com.mosabulgyeo.bewavoca.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Stage 엔티티 클래스
 * 게임의 스테이지 정보를 저장하고 관리하는 도메인 모델
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stage")
public class Stage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private int level;

	@Column(nullable = false)
	private String regionName;

	public Stage(String name, String description, int level, String regionName) {
		this.name = name;
		this.description = description;
		this.level = level;
		this.regionName = regionName;
	}

	@OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MiniStage> miniStages = new ArrayList<>();

	/**
	 * 미니 스테이지를 추가하는 메서드
	 *
	 * @param miniStage 추가할 미니 스테이지
	 */
	public void addMiniStage(MiniStage miniStage) {
		this.miniStages.add(miniStage);
	}
}