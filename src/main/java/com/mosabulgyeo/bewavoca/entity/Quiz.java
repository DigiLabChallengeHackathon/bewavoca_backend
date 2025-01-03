package com.mosabulgyeo.bewavoca.entity;

import java.util.List;

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

	/**
	 * 퀴즈가 속한 지역 (Region)
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id", nullable = false)
	private Region region;

	/**
	 * 스테이지 유형 (ox, match, choice)
	 */
	@Column(nullable = false)
	private String stageType;

	/**
	 * 퀴즈 질문
	 */
	@Column(nullable = true)
	private String question;

	/**
	 * 표준어 (match 유형에 사용)
	 */
	@Column(nullable = true)
	private String standard;

	/**
	 * 제주어 (정답 또는 매칭 단어)
	 */
	@Column(nullable = true)
	private String jeju;

	/**
	 * 4지선다 선택지 (쉼표로 구분된 문자열)
	 * 예: "바나나,사과,키위,포도"
	 */
	@ElementCollection
	@CollectionTable(name = "quiz_options", joinColumns = @JoinColumn(name = "quiz_id"))
	@Column(name = "option_value")
	private List<String> options;

	/**
	 * 정답 설명
	 */
	@Column(nullable = true)
	private String explanation;

	/**
	 * OX 유형의 정답 (true: O, false: X)
	 */
	@Column(nullable = true)
	private Boolean correctAnswer;

	/**
	 * Quiz 생성자
	 * @param region 지역
	 * @param stageType 스테이지 유형
	 * @param question 질문
	 * @param standard 표준어
	 * @param jeju 제주어 (정답)
	 * @param options 선택지 (쉼표로 구분된 문자열)
	 * @param explanation 정답 설명
	 * @param correctAnswer OX 정답
	 */
	public Quiz(Region region, String stageType, String question, String standard, String jeju,
		List<String> options, String explanation, Boolean correctAnswer) {
		this.region = region;
		this.stageType = stageType;
		this.question = question;
		this.standard = standard;
		this.jeju = jeju;
		this.options = options;
		this.explanation = explanation;
		this.correctAnswer = correctAnswer;
	}
}