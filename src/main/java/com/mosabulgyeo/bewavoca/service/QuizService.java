package com.mosabulgyeo.bewavoca.service;

import java.util.List;

import com.mosabulgyeo.bewavoca.dto.QuizResponse;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

	public QuizResponse getQuizByTypeAndLevel(String type, int level) {
		// Validate type
		if (!type.equals("ox") && !type.equals("match") && !type.equals("choice")) {
			throw new IllegalArgumentException("Invalid quiz type. Supported types: ox, match, choice.");
		}

		// Fetch quiz data based on type and level
		switch (type) {
			case "ox":
				return getOXQuiz(level);
			case "match":
				return getMatchQuiz(level);
			case "choice":
				return getChoiceQuiz(level);
			default:
				throw new IllegalArgumentException("Invalid quiz type.");
		}
	}

	private QuizResponse getOXQuiz(int level) {
		return new QuizResponse("ox", level, List.of(
			new QuizResponse.OXQuiz(1, "[바나나]는 제주어로 A다", true, "바나나는 제주어로 A입니당", "link"),
			new QuizResponse.OXQuiz(2, "[딸기]는 제주어로 B다", false, "딸기는 제주어로 B입니당", "link"),
			new QuizResponse.OXQuiz(3, "제주어로 [한라봉]은 제주도의 대표 과일로 주황색이다", true, "제주어로 한라봉은 제주도의 대표 과일로 주황색이다.", "link")
		));
	}

	private QuizResponse getMatchQuiz(int level) {
		return new QuizResponse("match", level, List.of(
			new QuizResponse.MatchQuiz("바나나", "A"),
			new QuizResponse.MatchQuiz("딸기", "B"),
			new QuizResponse.MatchQuiz("한라봉", "C"),
			new QuizResponse.MatchQuiz("바나나", "A")
		));
	}

	private QuizResponse getChoiceQuiz(int level) {
		return new QuizResponse("choice", level, List.of(
			new QuizResponse.ChoiceQuiz("바나나", "A", "바나나는 A입니다.", "link"),
			new QuizResponse.ChoiceQuiz("딸기", "B", "딸기는 B입니다.", "link"),
			new QuizResponse.ChoiceQuiz("한라봉", "C", "한라봉은 C입니다.", "link")
		));
	}
}