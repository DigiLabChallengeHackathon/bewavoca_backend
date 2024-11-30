package com.mosabulgyeo.bewavoca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * QuizResponse 클래스
 * 퀴즈 API의 응답 데이터를 포함하는 DTO 클래스.
 * 퀴즈 타입별 데이터 구조(OX Quiz, Match Quiz, Choice Quiz)를 포함.
 */
@Data
@AllArgsConstructor
public class QuizResponse {

	/**
	 * 퀴즈 타입 (e.g., "ox", "match", "choice").
	 */
	private String type;

	/**
	 * 퀴즈 레벨 (지역 또는 난이도 정보).
	 */
	private int level;

	/**
	 * 퀴즈 데이터 목록.
	 * 퀴즈 타입에 따라 다른 형식의 데이터를 포함할 수 있음.
	 */
	private List<?> quizzes;

	/**
	 * OX 퀴즈 데이터 클래스
	 * OX 퀴즈 형식의 데이터를 포함.
	 */
	@Data
	@AllArgsConstructor
	public static class OXQuiz {

		/**
		 * OX 퀴즈 고유 ID.
		 */
		@NotNull(message = "OX ID is required.")
		private Long oxId;

		/**
		 * OX 퀴즈 질문.
		 * 예: "[바나나]는 제주어로 A다"
		 */
		@NotBlank(message = "Question is required.")
		private String question;

		/**
		 * OX 퀴즈의 정답 (true: O, false: X).
		 */
		@NotNull(message = "Correct answer is required.")
		private boolean correctAnswer;

		/**
		 * OX 퀴즈의 정답 설명.
		 * 예: "바나나는 제주어로 A입니다."
		 */
		@NotBlank(message = "Explanation is required.")
		private String explanation;

		/**
		 * OX 퀴즈의 음성 파일 경로.
		 */
		@NotBlank(message = "Voice file path is required.")
		private String voice;
	}

	/**
	 * 매칭 퀴즈 데이터 클래스
	 * 표준어와 제주어의 매칭 데이터를 포함.
	 */
	@Data
	@AllArgsConstructor
	public static class MatchQuiz {

		/**
		 * 표준어 단어.
		 * 예: "바나나"
		 */
		@NotBlank(message = "Standard word is required.")
		private String standard;

		/**
		 * 대응되는 제주어 단어.
		 * 예: "A"
		 */
		@NotBlank(message = "Jeju word is required.")
		private String jeju;
	}

	/**
	 * 4지선다형 퀴즈 데이터 클래스
	 * 표준어, 제주어 정답, 정답 설명, 음성 파일 데이터를 포함.
	 */
	@Data
	@AllArgsConstructor
	public static class ChoiceQuiz {

		/**
		 * 4지선다형 퀴즈 고유 ID.
		 */
		@NotBlank(message = "ChoiceId is required.")
		private String choiceId;

		/**
		 * 4지 선다 질문.
		 * 예: "___는 노란색이고 너무 달콤한 과일이야."
		 */
		@NotBlank(message = "Question is required.")
		private String question;

		/**
		 * 정답 선택지 배열.
		 * 예: ["바나나", "사과", "키위", "포도"]
		 */
		@NotNull(message = "Options are required.")
		private String[] options;

		/**
		 * 정답 제주어 단어.
		 * 예: "바나나"
		 */
		@NotBlank(message = "Correct answer is required.")
		private String correctAnswer;

		/**
		 * 정답 설명.
		 * 예: "바나나는 제주어로 A입니다."
		 */
		private String explanation;

		/**
		 * 음성 파일 경로.
		 * 예: "link_to_voice_file"
		 */
		private String voice;
	}
}