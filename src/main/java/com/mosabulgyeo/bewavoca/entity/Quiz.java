package com.mosabulgyeo.bewavoca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Quiz 엔티티 클래스
 * 특정 지역(region)과 스테이지 유형(stage_type)에 따라 퀴즈를 저장하는 도메인 모델.
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "quiz")
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", nullable = false)
	private Region region;

	@Column(nullable = false)
	private String stageType; // OX, MATCH, CHOICE

	@Column(nullable = false)
	private String question;

	@Column(nullable = true)
	private String standard; // 표준어

	@Column(nullable = true)
	private String jeju; // 제주어

	@Column(nullable = true)
	private String explanation;

	@Column(nullable = true)
	private String voice;

	@Column(nullable = false)
	private boolean correctAnswer; // OX 퀴즈에서만 사용

	public Quiz(Region region, String stageType, String question, String standard, String jeju,
		String explanation, String voice, boolean correctAnswer) {
		this.region = region;
		this.stageType = stageType;
		this.question = question;
		this.standard = standard;
		this.jeju = jeju;
		this.explanation = explanation;
		this.voice = voice;
		this.correctAnswer = correctAnswer;
	}
}