package com.mosabulgyeo.bewavoca.service;

import com.mosabulgyeo.bewavoca.dto.CompleteQuizRequest;
import com.mosabulgyeo.bewavoca.dto.QuizResponse;
import com.mosabulgyeo.bewavoca.entity.Quiz;
import com.mosabulgyeo.bewavoca.entity.Region;
import com.mosabulgyeo.bewavoca.entity.User;
import com.mosabulgyeo.bewavoca.mapper.QuizResponseMapper;
import com.mosabulgyeo.bewavoca.repository.RegionRepository;
import com.mosabulgyeo.bewavoca.repository.QuizRepository;
import com.mosabulgyeo.bewavoca.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

@Service
public class QuizService {

	private final UserRepository userRepository;
	private final RegionRepository regionRepository;
	private final QuizRepository quizRepository;

	private final QuizResponseMapper quizResponseMapper;

	public QuizService(UserRepository userRepository, RegionRepository regionRepository, QuizRepository quizRepository, QuizResponseMapper quizResponseMapper) {
		this.userRepository = userRepository;
		this.regionRepository = regionRepository;
		this.quizRepository = quizRepository;
		this.quizResponseMapper = quizResponseMapper;
	}

	/**
	 * 주어진 type과 level(region의 level)을 기반으로 퀴즈를 조회
	 * @param type 퀴즈 타입 (ox, match, choice)
	 * @param level 지역 레벨
	 * @return QuizResponse
	 */
	public QuizResponse getQuizByTypeAndLevel(String type, int level) {
		Region region = regionRepository.findByLevel(level)
			.orElseThrow(() -> new IllegalArgumentException("Region not found"));

		List<Quiz> quizzes = quizRepository.findByRegionIdAndStageType(region.getId(), type);

		List<?> quizResponses = quizzes.stream()
			.map(quiz -> quizResponseMapper.mapQuizToResponse(type, quiz))
			.collect(Collectors.toList());

		return new QuizResponse(type, level, quizResponses);
	}

	/**
	 * 퀴즈 완료 처리
	 *
	 * @param request 퀴즈 완료 요청 정보
	 * @return 완료 메시지
	 */
	@Transactional
	public String completeQuiz(CompleteQuizRequest request) {
		User user = userRepository.findById(request.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("User not found"));

		if (request.getIsPassed()) {
			user.clearStage(request.getStageType().name());
			if (user.hasClearedRegion(request.getRegionId())) {
				user.completeRegion(request.getRegionId());
				return "Region completed successfully!";
			}
			return "Stage completed successfully.";
		} else {
			return "Stage failed. Retry is allowed.";
		}
	}
}