package com.mosabulgyeo.bewavoca.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * MiniStage 엔티티 클래스
 * 각 Stage에 포함되는 미니 스테이지 정보를 저장하고 관리.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mini_stage")
public class MiniStage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mini_stage_order", nullable = false)
	private int order; // 미니 스테이지의 순서 (1, 2, 3 등)

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stage_id", nullable = false)
	private Stage stage;

	public MiniStage(int order, Stage stage) {
		this.order = order;
		this.stage = stage;
	}
}