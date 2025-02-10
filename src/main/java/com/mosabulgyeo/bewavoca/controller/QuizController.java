package com.mosabulgyeo.bewavoca.controller;

import com.mosabulgyeo.bewavoca.dto.ApiResponse;
import com.mosabulgyeo.bewavoca.dto.CompleteQuizRequest;
import com.mosabulgyeo.bewavoca.dto.QuizResponse;
import com.mosabulgyeo.bewavoca.entity.User;
import com.mosabulgyeo.bewavoca.service.AuthService;
import com.mosabulgyeo.bewavoca.service.QuizService;
import com.mosabulgyeo.bewavoca.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

	private final QuizService quizService;
	private final AuthService authService;
	private final UserRepository userRepository;

	public QuizController(QuizService quizService, AuthService authService, UserRepository userRepository) {
		this.quizService = quizService;
		this.authService = authService;
		this.userRepository = userRepository;
	}

	/**
	 * Fetches the quiz data based on level and type.
	 *
	 * @param level The level of the quiz (e.g., 1, 2, 3).
	 * @param type  The type of the quiz (e.g., ox, match, choice).
	 * @return The appropriate quiz response.
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<QuizResponse>> getQuiz(@RequestParam int level, @RequestParam String type) {
		if (level < 1) {
			throw new IllegalArgumentException("Level must be greater than 0.");
		}
		QuizResponse quizResponse = quizService.getQuizByTypeAndLevel(type, level);
		return ResponseEntity.ok(new ApiResponse<>(
			"success",
			"Quiz retrieved successfully",
			quizResponse
		));
	}

	/**
	 * 퀴즈 완료 처리
	 *
	 * @param request 퀴즈 완료 요청 데이터
	 * @return 완료 메시지와 다음 스테이지 정보
	 */
	@PostMapping("/complete")
	public ResponseEntity<ApiResponse<Map<String, Integer>>> completeQuiz(@RequestBody @Valid CompleteQuizRequest request) {
		User user = userRepository.findByDeviceId(request.getDeviceId())
			.orElseThrow(() -> new IllegalArgumentException("User not found"));

		// 퀴즈 클리어 처리
		switch (request.getResultStatus()) {
			case GREAT_SUCCESS:
			case SUCCESS:
				authService.clearStage(request.getDeviceId(), request.getRegion(), request.getStage());

				// 🔥 클리어 후 최신 Progress 가져오기
				int nextRegion = user.getNextRegion();
				int nextStage = user.getNextStage();

				// ✅ 반환할 데이터 구성
				Map<String, Integer> responseData = new HashMap<>();
				responseData.put("stage", nextStage);
				responseData.put("region", nextRegion);

				return ResponseEntity.ok(new ApiResponse<>(
					"success",
					"Quiz completed successfully. Stage cleared.",
					responseData
				));

			case FAIL:
				return ResponseEntity.ok(new ApiResponse<>(
					"fail",
					"Quiz failed. Better luck next time!",
					null
				));
			default:
				throw new IllegalArgumentException("Invalid result status: " + request.getResultStatus());
		}
	}
}