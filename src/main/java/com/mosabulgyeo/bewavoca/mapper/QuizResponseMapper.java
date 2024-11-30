package com.mosabulgyeo.bewavoca.mapper;

import com.mosabulgyeo.bewavoca.dto.QuizResponse;
import com.mosabulgyeo.bewavoca.entity.Quiz;
import org.springframework.stereotype.Component;

/**
 * 퀴즈 매핑을 담당하는 클래스
 */
@Component
public class QuizResponseMapper {

	/**
	 * 퀴즈 데이터를 적절한 QuizResponse 타입으로 매핑
	 *
	 * @param type 퀴즈 타입 (ox, match, choice)
	 * @param quiz 퀴즈 엔티티
	 * @return QuizResponse의 적절한 서브 클래스 인스턴스
	 */
	public Object mapQuizToResponse(String type, Quiz quiz) {
		switch (type) {
			case "ox":
				return new QuizResponse.OXQuiz(
					quiz.getId(),
					quiz.getQuestion(),
					quiz.getCorrectAnswer(),
					quiz.getExplanation(),
					quiz.getVoice()
				);
			case "match":
				return new QuizResponse.MatchQuiz(
					quiz.getStandard(),
					quiz.getJeju()
				);
			case "choice":
				return new QuizResponse.ChoiceQuiz(
					quiz.getId().toString(),
					quiz.getQuestion(),
					quiz.getOptions().split(","),
					quiz.getJeju(),
					quiz.getExplanation(),
					quiz.getVoice()
				);
			default:
				throw new IllegalArgumentException("Unsupported quiz type: " + type);
		}
	}
}