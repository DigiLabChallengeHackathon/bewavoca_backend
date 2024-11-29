package com.mosabulgyeo.bewavoca.service;

import com.mosabulgyeo.bewavoca.dto.QuizResponse;
import com.mosabulgyeo.bewavoca.entity.Region;
import com.mosabulgyeo.bewavoca.repository.RegionRepository;
import com.mosabulgyeo.bewavoca.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

	private final RegionRepository regionRepository;
	private final QuizRepository quizRepository;

	public QuizService(RegionRepository regionRepository, QuizRepository quizRepository) {
		this.regionRepository = regionRepository;
		this.quizRepository = quizRepository;
	}

	/**
	 * 주어진 type과 level(region의 level)을 기반으로 퀴즈를 조회
	 * @param type 퀴즈 타입 (ox, match, choice)
	 * @param level 지역 레벨
	 * @return QuizResponse
	 */
	public QuizResponse getQuizByTypeAndLevel(String type, int level) {
		// Validate type
		if (!type.equals("ox") && !type.equals("match") && !type.equals("choice")) {
			throw new IllegalArgumentException("Invalid quiz type. Supported types: ox, match, choice.");
		}

		// Find the region by level
		Region region = regionRepository.findByLevel(level)
			.orElseThrow(() -> new IllegalArgumentException("Region with level " + level + " not found."));

		// Fetch quiz data based on region_id and stage_type
		List<?> quizzes = quizRepository.findByRegionIdAndStageType(region.getId(), type).stream()
			.map(quiz -> {
				if (type.equals("ox")) {
					return new QuizResponse.OXQuiz(
						quiz.getId(),
						quiz.getQuestion(),
						quiz.isCorrectAnswer(),
						quiz.getExplanation(),
						quiz.getVoice()
					);
				} else if (type.equals("match")) {
					return new QuizResponse.MatchQuiz(
						quiz.getStandard(),
						quiz.getJeju()
					);
				} else {
					return new QuizResponse.ChoiceQuiz(
						quiz.getStandard(),
						quiz.getJeju(),
						quiz.getExplanation(),
						quiz.getVoice()
					);
				}
			}).collect(Collectors.toList());

		return new QuizResponse(type, level, quizzes);
	}
}